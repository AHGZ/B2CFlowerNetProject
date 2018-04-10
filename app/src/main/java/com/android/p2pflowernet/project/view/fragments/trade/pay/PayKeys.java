package com.android.p2pflowernet.project.view.fragments.trade.pay;

public class PayKeys {
    //
    // 请参考 Android平台安全支付服务(msp)应用开发接口(4.2 RSA算法签名)部分，
    // 并使用压缩包中的openssl RSA密钥生成工具，生成一套RSA公私钥。
    // 这里签名时，只需要使用生成的RSA私钥。
    // Note: 为安全起见，使用RSA私钥进行签名的操作过程，应该尽量放到商家服务器端去进行。\

    //合作身份者id，以2088开头的16位纯数字 此id用来支付时快速登录
    public static final String DEFAULT_PARTNER = "2088821662372481";
    //收款支付宝账号
    public static final String DEFAULT_SELLER = "pay@huafanwang.com";
    //商户私钥，自助生成，在压缩包中有openssl，用此软件生成商户的公钥和私钥，写到此处要不然服务器返回错误。公钥要传到淘宝合作账户里详情请看淘宝的sdk文档
//    public static final String PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQD4v" +
//            "Wyejx2ntXENK6wInk8MraZyROuv9RNBCAL6HomWDm3mpARSJ8Fy1y5o8owNJKPoDsSWb/uZd3" +
//            "Zj2aO9JyOp6gvRnply9t4/3wYOV3UlXuocCBLM1dzriX5HdrWe9eSmkKacH591U+2sf85rqMJR7" +
//            "3ciSICtYZ14LZ2+6RMMeCsECAFVADwDPZ6B9KE5THvq/VXLTXGALlsWxutHXpmWz2/AdTrFl0NEJc1" +
//            "hS5div0PeooHwYcfUy49gV3hQg0f+PKFdBIRJFPMNwLNV2k/lyqxXmizi/AGlIfRn9JEYJCChqHDTdZ" +
//            "938gUAlCoaylNiaak5KKhu78Go58zK8bM7AgMBAAECggEBAPX4IzwcW//m3Xdfs0wGeDcaVNj0rHggijyplo" +
//            "ANyJJ5jFYh5R/0rkhI31zqVHH+xJKKX/4/mHZm+sy8gD1hQdQZXDyacDWOR7KzcUKnW3FsDWdQ2H50EXVdTmGO" +
//            "0wTxNX0813TVlJKqPwJwlAhEJnxtp8pgFdbeC6C2wyHxS2PsUdIbLio4VNkJMhQu9unO/Ib4nmx3K9P5neNaORyk" +
//            "4OyRyBQn1Uh6waqzNQkTzeH8yz3RKGZJPnK/LqKksuuO0j8VLwKf10UMryXi5FNN0uIr17/nHEfRVLOJBiY9nv6h6" +
//            "l3rNDJ6CmwJJ+2ckduOMTgUsfhP+7ns6PQYo4V6B3kCgYEA/NMn0t+eOUSLjsnqZQ76/9TLCS9+19YrX9SdqkYHhN5l" +
//            "Wj4ZieQz6hsUOFc6fJS7/4Sg7JHLphysidGxopYyDwn5stOccTVAY53gBRQ7nK9pgb0My1RDEyt4bo2z0oJJ+V9VU1VYD" +
//            "Xm3v+NvLTRXgM8QthuXaeeGSbwo658bNY0CgYEA+90it6iumMSKqmsxW3m0pyXcw6OubcHVX4SynbNwQNWWdDYZn00Dyue" +
//            "ECTqmnZmuke9TZR/Bka30cOuOEXw6rPGoFX2GY4H6RJ4/sJkXpG1Hx2NGG2kKAtfL2/InCCEGj7f0g/1PimSB80VQ4m" +
//            "70fxFFoAj5ps5zH4oMOtIDJecCgYAs8lEbIRwc7D2vReBA1Rf0UV1DJcl0D6QoZkEdW1PM4Ei+cVnS9Nnx3ZyLo55HNZ" +
//            "3ygB6n0AeXYXOfEMN2tyLxH4Hfs9UVHzVyQEfqPz2Vzm2AXh6fKzy9dLX1WYXFf9os1jDyHrMHukLbf1Btheekg6th5Kf" +
//            "XcQyjoSrZMLECpQKBgH2eYsV1dXeud3beNeTpA+hmIwDU75fAmJqmhavQJom/veXCYe8pK14VMK6luRpb30zcMBz8xL" +
//            "/TIlr7sYUZD+7YLLxjOhcBagC1aRC8mqUg+C8DXaEoN50JYp0V3s/YIPynhGYcfoKI9KOQC/KT4pBJ3g8eyt2elJ7Db" +
//            "KwduQI7AoGADzAsWc5/GKS9IhM7wUGdRc0lC34YZnPJWdpe9u45RV/4/JlCLaALvAKfDnA5/Bk2jql4jtlE7LM9PflBT" +
//            "4W2xPNsqOPzUzwpS5dexdV3dv5KJLzZ6KCYHRxyhq6GXtL4IUmn0AzAaTbybvMAJKTzK+AFK+q9CKObdKSt7LMtPnY=";


    public static final String PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQD4vWyejx2ntXENK6wInk8MraZyROuv9RNBCAL6HomWDm3mpARSJ8Fy1y5o8owNJKPoDsSWb/uZd3Zj2aO9JyOp6gvRnply9t4/3wYOV3UlXuocCBLM1dzriX5HdrWe9eSmkKacH591U+2sf85rqMJR73ciSICtYZ14LZ2+6RMMeCsECAFVADwDPZ6B9KE5THvq/VXLTXGALlsWxutHXpmWz2/AdTrFl0NEJc1hS5div0PeooHwYcfUy49gV3hQg0f+PKFdBIRJFPMNwLNV2k/lyqxXmizi/AGlIfRn9JEYJCChqHDTdZ938gUAlCoaylNiaak5KKhu78Go58zK8bM7AgMBAAECggEBAPX4IzwcW//m3Xdfs0wGeDcaVNj0rHggijyploANyJJ5jFYh5R/0rkhI31zqVHH+xJKKX/4/mHZm+sy8gD1hQdQZXDyacDWOR7KzcUKnW3FsDWdQ2H50EXVdTmGO0wTxNX0813TVlJKqPwJwlAhEJnxtp8pgFdbeC6C2wyHxS2PsUdIbLio4VNkJMhQu9unO/Ib4nmx3K9P5neNaORyk4OyRyBQn1Uh6waqzNQkTzeH8yz3RKGZJPnK/LqKksuuO0j8VLwKf10UMryXi5FNN0uIr17/nHEfRVLOJBiY9nv6h6l3rNDJ6CmwJJ+2ckduOMTgUsfhP+7ns6PQYo4V6B3kCgYEA/NMn0t+eOUSLjsnqZQ76/9TLCS9+19YrX9SdqkYHhN5lWj4ZieQz6hsUOFc6fJS7/4Sg7JHLphysidGxopYyDwn5stOccTVAY53gBRQ7nK9pgb0My1RDEyt4bo2z0oJJ+V9VU1VYDXm3v+NvLTRXgM8QthuXaeeGSbwo658bNY0CgYEA+90it6iumMSKqmsxW3m0pyXcw6OubcHVX4SynbNwQNWWdDYZn00DyueECTqmnZmuke9TZR/Bka30cOuOEXw6rPGoFX2GY4H6RJ4/sJkXpG1Hx2NGG2kKAtfL2/InCCEGj7f0g/1PimSB80VQ4m70fxFFoAj5ps5zH4oMOtIDJecCgYAs8lEbIRwc7D2vReBA1Rf0UV1DJcl0D6QoZkEdW1PM4Ei+cVnS9Nnx3ZyLo55HNZ3ygB6n0AeXYXOfEMN2tyLxH4Hfs9UVHzVyQEfqPz2Vzm2AXh6fKzy9dLX1WYXFf9os1jDyHrMHukLbf1Btheekg6th5KfXcQyjoSrZMLECpQKBgH2eYsV1dXeud3beNeTpA+hmIwDU75fAmJqmhavQJom/veXCYe8pK14VMK6luRpb30zcMBz8xL/TIlr7sYUZD+7YLLxjOhcBagC1aRC8mqUg+C8DXaEoN50JYp0V3s/YIPynhGYcfoKI9KOQC/KT4pBJ3g8eyt2elJ7DbKwduQI7AoGADzAsWc5/GKS9IhM7wUGdRc0lC34YZnPJWdpe9u45RV/4/JlCLaALvAKfDnA5/Bk2jql4jtlE7LM9PflBT4W2xPNsqOPzUzwpS5dexdV3dv5KJLzZ6KCYHRxyhq6GXtL4IUmn0AzAaTbybvMAJKTzK+AFK+q9CKObdKSt7LMtPnY=";

    //公钥
    public static final String PUBLIC = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlOKbVPZGzB6azH//d6abZlsFIZRw9JrS9/IDhyJ3hAv" +
            "FWzrDWqczKe5bjHpN" +
            "+aXFVo2MTDeW2TmQdZDVlIiWjXZM9a10GNJdv9w8stUk4P3sOUEPfueTsDtMNfaoHuwwha04cSqmsscGyCISkqHEO75sKEiRKX29S" +
            "5RoxBR/G+9Rh+uN/ho3HlGi3cWCooxpUPo5kI/6wMwGK5haY5j78RQN8O/qbrYF+R5z8WZYUmU1" +
            "ud+sXLcrq3INd3LshimV9HRZISLnQq+1ECF06V0nFTZSSYjmfXY12vddWmM7reMZz+aJ7QJCyisbzMNW0ukFeTPiL3DdD+wQxSoDwP2kjQIDAQAB";
    public static final String APP_ID = "2017111509941465";
}

