package com.bookshop01.goods.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

//GoodsController 인터페이스 정의
public interface GoodsController  {
	
	// 상품 상세 정보 요청 메서드
	// goods_id를 받아와서 상품의 상세정보를 조회하고 ModelAndView 객체에 정보를 담아 반환한다
	// HttpServletRequest와 HttpServletResponse는 HTTP 요청과 응답에 대한 객체를 의미한다
	public ModelAndView goodsDetail(@RequestParam("goods_id") String goods_id,
			HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	// 키워드 검색 요청 메서드
	// keyword를 받아와서 해당 키워드로 상품을 검색하고 검색한 결과를 JSON 형태로 응답한다
	// @ResponseBody 어노테이션을 사용하여 메서드가 직접 응답을 생성하도록 지정한다
	public @ResponseBody String keywordSearch(@RequestParam("keyword") String keyword,
			HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	// 상품 검색 요청 메서드
	// searchWord를 받아와서 해당 검색어로 상품을 검색하고 검색 결과를 ModelAndView 객체에 담아 반환한다
	public ModelAndView searchGoods(@RequestParam("searchWord") String searchWord,
			HttpServletRequest request, HttpServletResponse response) throws Exception;
}
