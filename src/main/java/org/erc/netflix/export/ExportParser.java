package org.erc.netflix.export;


import org.erc.Utils;
import org.erc.netflix.export.struct.Netflix;
import org.erc.netflix.export.struct.NetflixVideo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ExportParser {
    
    private static final String START_BLOCK = "netflix.falcorCache = ";
    private static final String END_BLOCK = ";</script>";

    private static final  String START_HTML = "<p class=\"fallback-text\">";

    private String jsonClean(String originalJSON){

        String content = originalJSON.replace("\\u00A0"," ");

        content = content.replace("\\\"","'");
        content = content.replace("\\uD83D"," ");
        content = content.replace("\\uDD14"," ");
        content = content.replace("\\x24type","type");
        content = content.replace("\",\"","\",\r\n\"");
        content =  Utils.unescape(content);
       
        
        return content;
    }

    private boolean jsonExtract(String html,StringBuilder builder) throws ExportParserException{
        boolean atLeastOne = false;
        int startPos = html.indexOf(START_BLOCK);
        if(startPos >-1){
            String htmlJSON = html.substring(startPos + START_BLOCK.length());
            int endPost = htmlJSON.indexOf(END_BLOCK);
            htmlJSON = htmlJSON.substring(0, endPost);
            htmlJSON = jsonClean(htmlJSON);
            ObjectMapper mapper = new ObjectMapper();
            try {
                

                Netflix content = mapper.readValue(htmlJSON,new TypeReference<Netflix>() {});
                addField(builder,"title");
                addField(builder,"type");
                addField(builder,"Release Year");
                addField(builder,"Seasons");
                addField(builder,"Seasons Desc");
                addField(builder,"Episodes");
                addField(builder,"Rate");
                addField(builder,"Rate desc");
                addField(builder,"Rate reason");
                builder.append("\r\n");

                for(NetflixVideo video  : content.getVideos().values()){
                    if( video.getItemSummary() == null){
                        continue;
                    }
                    atLeastOne = true;
                    String title = video.getItemSummary().getValue().getTitle();
                    String type =  video.getItemSummary().getValue().getType();
                    String releaseYear =  video.getItemSummary().getValue().getReleaseYear();
                    String seasonsLabel =  video.getItemSummary().getValue().getNumSeasonsLabel();
                    Integer seasons =  video.getItemSummary().getValue().getSeasonCount();
                    Integer episodes =  video.getItemSummary().getValue().getEpisodeCount();

                    String maturityValue =  video.getItemSummary().getValue().getMaturity().getRating().getValue();
                    String maturityDesc =  video.getItemSummary().getValue().getMaturity().getRating().getMaturityDescription();
                    String maturityWhy =  video.getItemSummary().getValue().getMaturity().getRating().getSpecificRatingReason();

                    addFieldIfExists(builder,title);
                    addFieldIfExists(builder,type);
                    addFieldIfExists(builder,releaseYear);
                    addFieldIfExists(builder,seasons);
                    addFieldIfExists(builder,seasonsLabel);
                    addFieldIfExists(builder,episodes);                        
                    addFieldIfExists(builder,maturityValue);
                    addFieldIfExists(builder,maturityDesc);
                    addFieldIfExists(builder,maturityWhy);
                    builder.append("\r\n");
                }
                
            } catch (Exception e) {
                throw new ExportParserException(e);
            }
        }
        if(!atLeastOne){
            builder.setLength(0);
        }
        return atLeastOne;
    }
    

    private void htmlExtract(String html,StringBuilder builder){
        
        // {"list_id":null,"location":"MyListAsGallery","rank":0,"request_id":"acc00cb2-2b74-41ab-ac4c-e45770505fb2-475700665","row":0,"track_id":254761469,"unifiedEntityId":"Video:80241318","video_id":80241318,"image_key":"sdp|2dfb4c70-44fa-11ed-8e26-12f2dcdaaa25|es-ES|CzZ","supp_video_id":1,"lolomo_id":"unknown","maturityMisMatchEdgy":false,"maturityMisMatchNonEdgy":false,"titleInformationDensity":"","titleInformationDensityExplored":"","appView":"boxArt","usePresentedEvent":true}
        addField(builder,"title");
        builder.append("\r\n");

        int pos = -1;
        do{
            pos = html.indexOf(START_HTML);
            if(pos>-1){
                html = html.substring(pos);
                int pos2 = html.indexOf("</p>");
                String text =  html.substring(START_HTML.length(),pos2);
                addField(builder,text);
                builder.append("\r\n");
                html = html.substring(pos2);
            }
        }while(pos>-1);
    }

    @SuppressWarnings({"Unchecked"})
    public String parse(String html) throws ExportParserException{
        StringBuilder builder = new StringBuilder();

        if(html!=null && !html.isBlank()){
            if(!jsonExtract(html,builder)){
                // NO JSON with all information available. 
                htmlExtract(html,builder);
            }

            return builder.toString();
        }
       
        return null;
    }

    private void addField(StringBuilder builder, Object item){
        builder.append("\"").append(item).append("\"").append(",");
    }

    private void addFieldIfExists(StringBuilder builder, Object item){
        if(item!=null){
            addField(builder,item);
        }else{
            builder.append(",");
        }
    }
}
