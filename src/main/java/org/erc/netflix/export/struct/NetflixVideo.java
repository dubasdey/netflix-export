package org.erc.netflix.export.struct;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NetflixVideo {

    @Getter
    @Setter
    @ToString
    @JsonInclude(value = Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class NetflixVideoItemSummary {
        /**
         * Type
         */
        private String type;

        /**
         * Title
         */
        private String title;

        private String releaseYear;

        private String numSeasonsLabel;

        private Integer seasonCount;

        private Integer episodeCount;

        private NetflixVideoItemSummaryMaturity maturity;
    }

    @Getter
    @Setter
    @ToString
    @JsonInclude(value = Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class NetflixVideoItemSummaryMaturity {
        private NetflixVideoItemSummaryMaturityRating rating;
    }

    @Getter
    @Setter
    @ToString
    @JsonInclude(value = Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class NetflixVideoItemSummaryMaturityRating {
        private String value;
        private String maturityDescription;
        private String specificRatingReason;
    }

    private TypedValue<NetflixVideoItemSummary> itemSummary;
}
