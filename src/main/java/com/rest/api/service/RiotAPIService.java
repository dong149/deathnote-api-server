//package com.rest.api.service;
//
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class RiotAPIService {
//    public List<String> recommendList(String memberId) {
//        // 회원 아이디로 대여한 도서의 isbn 조회
//        List<String> bookIsbn = rentMapper.selectIsbn(memberId);
//        //api호출하는 uri
//        String uri = "http://data4library.kr/api/recommandList?authKey=d30acf5969608e57f856c36ddd03e661e138d851efb484a2ddffc33ce70627d4" + "&isbn13=";
//        String line = null;
//        //조회한 도서 isbn을 담으려고 list 형태 객체화
//        List<String> isbnList = new ArrayList<String>();
//        //조회된 도서 isbn 크기만큼 반복문
//        for(int i =0; i < bookIsbn.size(); i++) { uri += bookIsbn.get(i)+";"; }
//        uri += "&format=json";
////        logger.info("uri : {}",uri);
//        // 완성된 uri 출력
//        // try { //uri 실행 URL url = new URL(uri); URLConnection conn = url.openConnection(); BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8")); line = br.readLine(); //실행하면 json타입을 데이터가 한줄의 String 타입으로 가져와지기 때문에 json 형태로 가공해줘야한다. //json 라이브러리가 추가 되어있어야 사용할 수 있는 메서드 JSONParser parser = new JSONParser(); JSONObject obj = (JSONObject) parser.parse(line); JSONObject response = (JSONObject) obj.get("response"); JSONArray docs = (JSONArray) response.get("docs"); //최대 200권의 도서를 추천해주는데 100권만 가져와서 리스트 형태로 담기 for(int i=0; i < 100;i++) { JSONObject books = (JSONObject) docs.get(i); JSONObject book = (JSONObject) books.get("book"); String isbn13 = (String) book.get("isbn13"); if(isbn13 != null) { isbnList.add(isbn13); } } } catch (IOException e) { e.printStackTrace(); } catch (ParseException e) { e.printStackTrace(); } logger.info("isbnList : {}",isbnList); return isbnList; }
//
//
//    }
