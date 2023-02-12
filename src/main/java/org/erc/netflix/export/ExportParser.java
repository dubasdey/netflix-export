package org.erc.netflix.export;

import java.util.ArrayList;
import java.util.List;

import org.erc.Utils;
import org.erc.netflix.export.struct.Netflix;
import org.erc.netflix.export.struct.NetflixVideo;
import org.erc.netflix.export.struct.TypedValue;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ExportParser {
    
    private static final String START_BLOCK = "netflix.falcorCache = ";
    private static final String END_BLOCK1 = "}}}}}";
    private static final String END_BLOCK2 = ";</script>";

    private String jsonClean(String originalJSON){
        String content =  Utils.unescape(originalJSON);
        // Other fixes
        content = content.replace("$type","type");
        return content;
    }

    @SuppressWarnings({"Unchecked"})
    public String parse(String html) throws ExportParserException{
        String result = null;

        if(html!=null && !html.isBlank()){
            int startPos = html.indexOf(START_BLOCK);
            if(startPos >-1){
                String htmlJSON = html.substring(startPos + START_BLOCK.length());
                int endPost = htmlJSON.indexOf(END_BLOCK1 + END_BLOCK2);
                htmlJSON = jsonClean(htmlJSON.substring(0, endPost+END_BLOCK1.length()));
                
                ObjectMapper mapper = new ObjectMapper();
                try {
                    Netflix content = mapper.readValue(htmlJSON,new TypeReference<Netflix>() {});
                    List<String> videoIds = new ArrayList<>();

                    // My list video IDS
                    for (TypedValue<Object> item : content.getMylist().values()){
                        if("ref".equals(item.getType()) && item.getValue() !=null && item.getValue() instanceof ArrayList){
                            List<String> links = (List<String>) item.getValue();
                            if(links.size() == 2 && "videos".equals(links.get(0)) ){
                                videoIds.add(links.get(1));
                            }
                            //System.out.println(item.getValue().getClass());
                            //System.out.println(item.getValue());
                        }
                    }
                    StringBuilder builder = new StringBuilder();

                    addField(builder,"title");
                    addField(builder,"type");
                    addField(builder,"Release Year");
                    addField(builder,"Seasons");
                    addField(builder,"Seasons Desc");
                    addField(builder,"Episodes");
                    addField(builder,"Rate");
                    addField(builder,"Rate desc");
                    addField(builder,"Rate reason");

                    for(NetflixVideo video  : content.getVideos().values()){
                        //NetflixVideo video = content.getVideos().get(videoId);
                        
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
                    result = builder.toString();
                } catch (Exception e) {
                    throw new ExportParserException(e);
                }
            }
        }
        return result;
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
