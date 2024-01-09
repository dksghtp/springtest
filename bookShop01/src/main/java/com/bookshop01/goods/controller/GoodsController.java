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
	public ModelAndView goodsDetail(@RequestParam("goods_id") String goods_id,
			HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	// 키워드 검색 요청 메서드
	public @ResponseBody String keywordSearch(@RequestParam("keyword") String keyword,
			HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	// 상품 검색 요청 메서드
	public ModelAndView searchGoods(@RequestParam("searchWord") String searchWord,
			HttpServletRequest request, HttpServletResponse response) throws Exception;
}
