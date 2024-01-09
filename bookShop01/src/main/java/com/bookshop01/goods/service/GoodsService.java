package com.bookshop01.goods.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.bookshop01.goods.vo.GoodsVO;

//GoodsService 인터페이스 정의
public interface GoodsService {
	
	// 모든 상품 목록을 카테고리별로 분류하여 가져오는 메서드
	public Map<String,List<GoodsVO>> listGoods() throws Exception;
	
	// 특정 상품의 상세 정보를 가져오는 메서드
	public Map goodsDetail(String _goods_id) throws Exception;
	
	// 특정 키워드에 대한 검색 결과를 가져오는 메서드
	public List<String> keywordSearch(String keyword) throws Exception;
	
	// 특정 검색어에 대한 상품 목록을 가져오는 메서드
	public List<GoodsVO> searchGoods(String searchWord) throws Exception;
}
