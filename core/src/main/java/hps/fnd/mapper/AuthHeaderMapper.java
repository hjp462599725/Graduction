package hps.fnd.mapper;

import java.util.List;
import com.hand.hap.mybatis.common.Mapper;

import hps.fnd.dto.AuthHeader;

/**
 * @name AuthHeaderMapper
 * @description 角色权限mapper
 * @author hongan.dong@hand-china.com 2016年9月13日下午1:33:31
 * @version 1.0
 */
public interface AuthHeaderMapper extends Mapper<AuthHeader> {

	/**
	 * @description 权限头表查询
	 * @param authHeader
	 * @return List<AuthHeader>
	 */
	public List<AuthHeader> selectAuthHeaders(AuthHeader authHeader);

	/**
	 * @description 权限头表查询
	 * @param authHeaders
	 * @return AuthHeader
	 */
	public AuthHeader selectAuthHeader(AuthHeader authHeader);

	/**
	 * @description 更新权限头表
	 * @param authHeader
	 *            DTO
	 */
	public void updateAuthHeaders(AuthHeader authHeader);

	/**
	 * @description 插入权限头表
	 * @param authHeader
	 *            DTO
	 */
	public void insertAuthHeaders(AuthHeader authHeader);

	/**
	 * @description redis初始化
	 * @return List<AuthHeader>
	 */
	List<AuthHeader> redisInitHeaders();
}
