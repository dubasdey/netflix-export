package org.erc.netflix.export.struct;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// ProfileList
// profiles
// avatars
// notifications
// headerLinks
// socialLinks
// sessionContext
// mylist
//      list obj video references
//          "40": {"type": "ref","value": ["videos","70021662"]
// videos
//      list of video IDS
//          "70021662": {
//              "availability": { "type": "atom", "value": { "isPlayable": true, "availabilityDate": "31 de julio", "availabilityStartTime": 1659304800000, "unplayableCause": null } },
//              "episodeCount": { "type": "atom", "value": null },
//              "summary": { "type": "atom", "value": { "type": "movie", "unifiedEntityId": "Video:70021662", "id": 70021662, "isOriginal": false, "liveEvent": { "hasLiveEvent": false } } },
//              "queue": { "type": "atom", "value": { "available": true, "inQueue": true } },
//              "inRemindMeList": { "type": "atom", "value": false },
//              "itemSummary": {
//                  "type": "atom",
//        "value": {
//            "requestId": "36ba1869-8624-4174-890a-34f14ad641d3-453209662",
//            "unifiedEntityId": "Video:70021662",
//            "id": 70021662,
//            "type": "movie",
//            "isOriginal": false,
//            "liveEvent": {},
//            "maturity": {
//                "rating": {
//                    "value": "12+",
//                    "maturityDescription": "No recomendada para menores de 12 años",
//                    "specificRatingReason": "",
//                    "maturityLevel": 80,
//                    "board": "Spain",
//                    "boardId": 8602,
//                    "ratingId": 8604,
//                    "reasons": []
//                }
//            },
//            "availability": { "isPlayable": true, "availabilityDate": "31 de julio", "availabilityStartTime": 1659304800000, "unplayableCause": null },
//            "videoId": 70021662,
//            "userRatingRequestId": "36ba1869-8624-4174-890a-34f14ad641d3-453209662",
//            "releaseYear": 2005,
//            "numSeasonsLabel": null,
//            "title": "Memorias de una geisha",
//            "titleMaturity": { "level": 80 },
//            "infoDensityRuntime": 8710,
//            "boxArt": {
//                "videoId": 70021662,
//                "url": "http://occ-0-5471-358.1.nflxso.net/dnm/api/v6/6gmvu2hxdfnQ55LZZjyzYR4kzGk/AAAABTfcQgiT4YbDuSlHeCezOMb2hB9Sk6fgfNiT4V79XH0FTSTfRQFRTSNQYramNzdWROPmokjYQ-gQXbhKvwlebON8dfgrShrUzbk.webp?r=310",
//                "width": 342,
//                "height": 192,
//                "isSmoky": false,
//                "extension": "webp",
//                "size": "_342x192",
//                "imageKey": "sdp|d647ed81-c4a8-11eb-941c-12289d1dfd17|es-ES"
//            }
//        }
//    }
//},
@Getter
@Setter
@ToString
@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Netflix {
   
    /**
     * 
     */
    private Map<String,TypedValue<Object>> mylist;

    /**
     *
     */
    private Map<String,NetflixVideo> videos;

}
