package com.bookshop01.goods.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bookshop01.common.base.BaseController;
import com.bookshop01.goods.service.GoodsService;
import com.bookshop01.goods.vo.GoodsVO;

import net.sf.json.JSONObject;


//이 클래스를 Spring MVC 컨트롤러로 지정
@Controller("goodsController")
@RequestMapping(value="/goods")
public class GoodsControllerImpl extends BaseController   implements GoodsController {
	
	// GoodsService를 자동으로 주입
	@Autowired
	private GoodsService goodsService;
	
	// 상품 상세 정보 요청을 처리
	@RequestMapping(value="/goodsDetail.do" ,method = RequestMethod.GET)
	public ModelAndView goodsDetail(@RequestParam("goods_id") String goods_id,
			                       HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 현재 요청의 속성(attribute) 중 "viewName"이라는 속성을 가져와서 문자열 변수 viewName에 저장합니다.
		// 이는 뷰의 이름을 나타냅니다.
		String viewName=(String)request.getAttribute("viewName");
		
		// 현재 요청에 대한 세션을 가져옵니다. 세션은 클라이언트와 서버 간의 상태를 유지하기 위한 객체입니다.
		HttpSession session=request.getSession();
		
		// goodsService라는 서비스 객체의 goodsDetail 메서드를 호출하여 
		// goods_id에 해당하는 상품의 상세 정보를 Map 형태로 가져옵니다.
		Map goodsMap=goodsService.goodsDetail(goods_id);
		
		// ModelAndView 객체를 생성하고, 생성자에 뷰의 이름을 전달합니다.
		// mav 객체에 "goodsMap"이라는 이름으로 goodsMap을 추가합니다. 
		// 이렇게 함으로써 뷰로 데이터를 전달할 수 있습니다.
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("goodsMap", goodsMap);
		
		// goodsMap에서 "goodsVO"라는 키로 상품 정보를 가져와 
		// GoodsVO 타입으로 캐스팅하여 goodsVO 변수에 저장합니다.
		// addGoodsInQuick 메서드를 호출하여 최근 본 상품 리스트에 현재 상품을 추가합니다.
		// 이 메서드에 대한 설명은 다음에 이어집니다.
		GoodsVO goodsVO=(GoodsVO)goodsMap.get("goodsVO");
		addGoodsInQuick(goods_id,goodsVO,session);
		
		//최종적으로 구성된 ModelAndView 객체를 반환합니다. 이 객체는 뷰의 이름과 상품 정보를 가지고 있습니다.
		return mav;
	}
	
	// 키워드 검색 요청을 처리합니다.
	@RequestMapping(value="/keywordSearch.do",method = RequestMethod.GET,produces = "application/text; charset=utf8")
	public @ResponseBody String  keywordSearch(@RequestParam("keyword") String keyword,
			                                  HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		// 응답의 헤더를 "text/html"로 설정하고, 문자셋을 UTF-8로 지정합니다.
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		//System.out.println(keyword);
		
		// 클라이언트로부터 받은 keyword가 null이거나 빈 문자열인 경우, 아무런 처리 없이 null을 반환합니다.
		if(keyword == null || keyword.equals(""))
		   return null ;
	
		// 검색을 수행하기 전에 keyword를 대문자로 변환합니다. 이후의 검색은 대소문자를 무시하게 됩니다.
		keyword = keyword.toUpperCase();
		
		// goodsService를 통해 대문자로 변환된 keyword에 대한 상품 검색을 수행합니다.
	    List<String> keywordList =goodsService.keywordSearch(keyword);
	    
		 // 검색 결과를 담을 JSONObject를 생성하고, 그 안에 "keyword"라는 키로 검색 결과를 추가합니다.
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("keyword", keywordList);
		 
		// 최종적으로 jsonObject를 JSON 형식의 문자열로 변환하고, 해당 문자열을 반환합니다.
	    String jsonInfo = jsonObject.toString();
	   // System.out.println(jsonInfo);
	    return jsonInfo ;
	}
	
	// 상품 검색 요청을 처리합니다.
	@RequestMapping(value="/searchGoods.do" ,method = RequestMethod.GET)
	public ModelAndView searchGoods(@RequestParam("searchWord") String searchWord,
			                       HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		// 뷰의 이름을 가져옵니다.
		String viewName=(String)request.getAttribute("viewName");
		
		// GoodsService를 통해 상품 검색을 수행합니다.
		List<GoodsVO> goodsList=goodsService.searchGoods(searchWord);
		
		// ModelAndView 객체를 생성하고 뷰 이름과 상품 리스트를 설정합니다.
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("goodsList", goodsList);
		
		return mav;
		
	}
	
	// 최근 본 상품 리스트에 상품을 추가하는 메서드입니다.
	private void addGoodsInQuick(String goods_id,GoodsVO goodsVO,HttpSession session){
		boolean already_existed=false;
		List<GoodsVO> quickGoodsList;  //최근 본 상품 저장 ArrayList
		
		// 세션에서 최근 본 상품 리스트를 가져옵니다.
		quickGoodsList=(ArrayList<GoodsVO>)session.getAttribute("quickGoodsList");
		
		if(quickGoodsList!=null){
			if(quickGoodsList.size() < 4){ //미리본 상품 리스트에 상품개수가 세개 이하인 경우
				for(int i=0; i<quickGoodsList.size();i++){
					GoodsVO _goodsBean=(GoodsVO)quickGoodsList.get(i);
					if(goods_id.equals(_goodsBean.getGoods_id())){
						already_existed=true;
						break;
					}
				}
				if(already_existed==false){
					quickGoodsList.add(goodsVO);
				}
			}
			
		}else{
			quickGoodsList =new ArrayList<GoodsVO>();
			quickGoodsList.add(goodsVO);
			
		}
		
		// 세션에 최근 본 상품 리스트를 저장
		session.setAttribute("quickGoodsList",quickGoodsList);
		session.setAttribute("quickGoodsListNum", quickGoodsList.size());
	}
}
