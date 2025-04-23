package com.example.seoulpublicdata2025backend.domain.naverReceipt.dto;

import java.util.List;
import lombok.Data;

@Data
public class NaverOcrResponseDto {
    private String version;
    private String requestId;
    private long timestamp;
    private List<ImageResult> images;

    @Data
    public static class ImageResult {
        private String uid;
        private String name;
        private String inferResult;
        private String message;
        private ReceiptWrapper receipt;

        @Data
        public static class ReceiptWrapper {
            private Meta meta;
            private ReceiptResult result;

            @Data
            public static class Meta {
                private String estimatedLanguage;
            }

            @Data
            public static class ReceiptResult {
                private StoreInfo storeInfo;
                private PaymentInfo paymentInfo;
                private List<SubResult> subResults;
                private TotalPrice totalPrice;

                @Data
                public static class StoreInfo {
                    private Field name;
                    private Field subName;
                    private Field bizNum;
                    private List<Field> addresses;
                    private List<Field> tel;
                }

                @Data
                public static class PaymentInfo {
                    private Field date;
                    private Field time;
                    private CardInfo cardInfo;
                    private Field confirmNum;
                }

                @Data
                public static class SubResult {
                    private List<Item> items;
                }

                @Data
                public static class Item {
                    private Field name;
                    private Field count;
                    private Price price;
                }

                @Data
                public static class TotalPrice {
                    private Field price;
                }

                @Data
                public static class CardInfo {
                    private Field company;
                    private Field number;
                }

                @Data
                public static class Price {
                    private Field price;
                    private Field unitPrice;
                }

                @Data
                public static class Field {
                    private String text;
                    private Formatted formatted;
                    private String keyText;
                    private double confidenceScore;
                }

                @Data
                public static class Formatted {
                    private String value;
                    private String year;
                    private String month;
                    private String day;
                    private String hour;
                    private String minute;
                    private String second;
                }
            }
        }
    }
}

