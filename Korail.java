import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Korail {
    public static void main(String[] args) throws Exception {

        String url = "https://www.letskorail.com/ebizprd/EbizPrdTicketPr12111_i1.do";

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Cache-Control", "max-age=0");
        headers.put("sec-ch-ua", "\"Google Chrome\";v=\"93\", \" Not;A Brand\";v=\"99\", \"Chromium\";v=\"93\"");
        headers.put("sec-ch-ua-mobile", "?0");
        headers.put("sec-ch-ua-platform", "\"Windows\"");
        headers.put("Upgrade-Insecure-Requests", "1");
        headers.put("Origin", "https://www.letskorail.com");
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.63 Safari/537.36");
        headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        headers.put("Sec-Fetch-Site", "same-origin");
        headers.put("Sec-Fetch-Mode", "navigate");
        headers.put("Sec-Fetch-User", "?1");
        headers.put("Sec-Fetch-Dest", "document");
        headers.put("Referer", "https://www.letskorail.com/ebizprd/EbizPrdTicketPr21111_i1.do");
        headers.put("Accept-Encoding", "gzip, deflate, br");
        headers.put("Accept-Language", "ko-KR,ko;q=0.9");
        headers.put("Cookie", "_ga=GA1.1.1821726049.1631832920; WMONID=jOjHCzgk9tD; JSESSIONID=O7Cq2Z92oj59rDdI81pIQLLHEBL0YVIei28zpCgnXnotXItJpbgRs8EugG1QPGQN; _ga_LP2TSNTFG1=GS1.1.1631855495.5.1.1631855596.0");

        String requestBody = "run=&selGoTrain=00&txtPsgFlg_1=1&txtPsgFlg_2=0&txtPsgFlg_8=0&txtPsgFlg_3=0&txtPsgFlg_4=0&txtPsgFlg_5=0&txtSeatAttCd_3=000&txtSeatAttCd_2=000&txtSeatAttCd_4=015&selGoTrainRa=00&radJobId=1&txtGoStart=%EC%84%9C%EC%9A%B8&txtGoEnd=%EC%9A%B8%EC%82%B0%28%ED%86%B5%EB%8F%84%EC%82%AC%29&txtGoStartCode=&txtGoEndCode=&selGoYear=2021&selGoMonth=09&selGoDay=18&selGoHour=08&txtGoHour=&txtGoYoil=%ED%86%A0&selGoSeat1=015&selGoSeat2=015&txtPsgCnt1=0&txtPsgCnt2=0&txtGoPage=1&txtGoAbrdDt=&selGoRoom=&useSeatFlg=&useServiceFlg=&checkStnNm=Y&txtMenuId=11&SeandYo=&txtGoStartCode2=&txtGoEndCode2=&selGoStartDay=&hidEasyTalk=&ra=1&txtSeatAttCd1=000&txtSeatAttCd2=000&txtSeatAttCd3=000&txtSeatAttCd4=015&txtSeatAttCd4_1=&txtSeatAttCd5=000&txtTotPsgCnt=1&hidRsvTpCd=03&txtPsgTpCd1=1&txtPsgTpCd2=3&txtPsgTpCd3=1&txtPsgTpCd4=&txtPsgTpCd5=1&txtPsgTpCd6=&txtPsgTpCd7=1&txtPsgTpCd8=3&txtPsgTpCd9=&txtDiscKndCd1=000&txtDiscKndCd2=000&txtDiscKndCd3=111&txtDiscKndCd4=&txtDiscKndCd5=131&txtDiscKndCd6=&txtDiscKndCd7=112&txtDiscKndCd8=321&txtDiscKndCd9=&txtCompaCnt1=1&txtCompaCnt2=0&txtCompaCnt3=0&txtCompaCnt4=0&txtCompaCnt5=0&txtCompaCnt6=0&txtCompaCnt7=0&txtCompaCnt8=0&txtCompaCnt9=&txtStndFlg=&txtJobId=1101&txtJrnyCnt=1&txtPsrmClCd1=1&txtJrnySqno1=001&txtJrnyTpCd1=11&txtDptDt1=20210918&txtDptRsStnCd1=&txtDptRsStnCdNm1=%EC%84%9C%EC%9A%B8&txtDptTm1=080000&txtArvRsStnCd1=&txtArvRsStnCdNm1=%EC%9A%B8%EC%82%B0%28%ED%86%B5%EB%8F%84%EC%82%AC%29&txtArvTm1=&txtTrnNo1=&txtRunDt1=&txtTrnClsfCd1=00&txtTrnGpCd1=&txtChgFlg1=N&txtPsrmClCd2=1&txtJrnySqno2=&txtJrnyTpCd2=&txtDptDt2=&txtDptRsStnCd2=&txtDptRsStnCdNm2=&txtDptTm2=&txtArvRsStnCd2=&txtArvRsStnCdNm2=&txtArvTm2=&txtTrnNo2=&txtRunDt2=&txtTrnClsfCd2=&txtTrnGpCd2=&txtChgFlg2=";

        Random random = new Random();

        while (true) {
            try {
                Document document = Jsoup.connect(url)
                        .ignoreContentType(true)
                        .headers(headers)
                        .requestBody(requestBody)
                        .post();

                int index = document.text().indexOf("잔여석없음");
                if (index == -1) {
                    System.out.println(document);
                    break;
                }

                int rand = random.nextInt(500);
                TimeUnit.MILLISECONDS.sleep(1000 + rand);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        sendTelegram();
    }

    public static void sendTelegram() throws Exception {

        Jsoup.connect("https://fury.kabang.app/send/me")
                .requestBody("ok")
                .ignoreContentType(true)
                .post();
    }
}
