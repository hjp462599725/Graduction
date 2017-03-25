package hps.fnd.mapper;

import java.util.List;

import com.hand.hap.mybatis.common.Mapper;

import hps.fnd.dto.AuthHeader;
import hps.fnd.dto.AuthLine;

/**
 * @name AuthLineMapper
 * @description 权限行表Mapper
 * @author hongan.dong@hand-china.com 2016年9月13日下午1:37:25
 * @version 1.0
 */
public interface AuthLineMapper extends Mapper<AuthLine> {

	/**
	 * @description 权限行查询
	 * @param authLine
	 *            DTO
	 * @return AuthLine
	 */
	public AuthLine selectAuthLine(AuthLine authLine);

	/**
	 * @description 权限行表查询
	 * @param authHeaders
	 *            DTO
	 * @return List<AuthLine>
	 */
	List<AuthLine> selectAuthLines(AuthHeader authHeaders);

	/**
	 * @description 插入权限行表
	 * @param authLine
	 */
	public void insertAuthLines(AuthLine authLine);

	/**
	 * @description 更新权限行表
	 * @param authLine
	 */
	public void updateAuthLines(AuthLine authLine);

	/**
	 * @description 查询角色页面的权限
	 * @param authLine
	 *            DTO
	 * @return List<AuthLine>
	 */
	List<AuthLine> selectAuthList(AuthLine authLine);

	/**
	 * @description 删除行
	 * @param a
	 *            DTO
	 */
	public void deleteAuthLines(AuthLine authLine);

	/**
	 * @description redis初始化
	 * @param AuthHeaderId
	 * @return List<AuthLine>
	 */
	List<AuthLine> redisInitLines(Long AuthHeaderId);

}
