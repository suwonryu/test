import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class NaverNews {
    public static void main(String[] args) throws Exception {

        String url = "https://s.search.naver.com/p/newssearch/search.naver?";

        LocalDate today = LocalDate.now().minusDays(3);

        DateTimeFormatter format1 = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        DateTimeFormatter format2 = DateTimeFormatter.ofPattern("yyyyMMdd");

        String str1 = today.format(format1);
        String str2 = today.format(format2);

        url += "de=" + str1 + "&ds=" + str1 + "&eid=&field=0&force_original=&is_dts=1&is_sug_officeid=0&mynews=0&news_office_checked=&nlu_query=&nqx_theme=%7B%22theme%22%3A%7B%22main%22%3A%7B%22name%22%3A%22corporation_hq%22%7D%2C%22sub%22%3A%5B%7B%22name%22%3A%22stock%22%7D%5D%7D%7D&nso=%26nso%3Dso%3Add%2Cp%3Afrom" + str2 + "to" + str2 + "%2Ca%3Aall&nx_and_query=%22%EC%B9%B4%EC%B9%B4%EC%98%A4%EB%B1%85%ED%81%AC%22&nx_search_hlquery=%22%EC%B9%B4%EC%B9%B4%EC%98%A4%EB%B1%85%ED%81%AC%22&nx_search_query=%EC%B9%B4%EC%B9%B4%EC%98%A4%EB%B1%85%ED%81%AC&nx_sub_query=&office_category=0&office_section_code=0&office_type=0&pd=3&photo=0&query=%22%EC%B9%B4%EC%B9%B4%EC%98%A4%EB%B1%85%ED%81%AC%22&query_original=&service_area=0&sort=1&spq=0&start=1&where=news_mtab_api&nso=so:dd,p:from" + str2 + "to" + str2 + ",a:all&_callback=&_=1720702850116";

        while (true) {
            try {
                String body = Jsoup.connect(url).execute().body();
                NewsResponse newsResponse = new Gson().fromJson(body, NewsResponse.class);

                for (String content : newsResponse.contents) {
                    Document document = Jsoup.parse(content);
                    System.out.println(document.select("div.api_txt_lines.tit").text());
                    System.out.println(document.select("a.news_tit").attr("href"));
                    System.out.println("-----------------------------------------------------------");
                }

                url = newsResponse.nextUrl;
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (Exception e) {
                return;
            }
        }
    }
}


class NewsResponse {
    public List<String> contents;
    public String nextUrl;
}