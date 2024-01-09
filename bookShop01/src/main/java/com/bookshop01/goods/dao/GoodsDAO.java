package com.bookshop01.goods.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.bookshop01.goods.vo.GoodsVO;
import com.bookshop01.goods.vo.ImageFileVO;

//GoodsDAO 인터페이스 정의
public interface GoodsDAO {
	
	// 상품 목록 조회 메서드
	public List<GoodsVO> selectGoodsList(String goodsStatus ) throws DataAccessException;
	
	// 키워드 검색 메서드
	public List<String> selectKeywordSearch(String keyword) throws DataAccessException;
	
	// 상품 상세 정보 조회 메서드
	public GoodsVO selectGoodsDetail(String goods_id) throws DataAccessException;
	
	// 상품 상세 이미지 파일 목록 조회 메서드
	public List<ImageFileVO> selectGoodsDetailImage(String goods_id) throws DataAccessException;
	
	// 검색어로 상품 목록 조회 메서드
	public List<GoodsVO> selectGoodsBySearchWord(String searchWord) throws DataAccessException;
}
