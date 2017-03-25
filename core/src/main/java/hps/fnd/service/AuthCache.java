package hps.fnd.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hand.hap.cache.impl.HashStringRedisCache;
import com.hand.hap.core.annotation.FreeMarkerBean;

import hps.fnd.dto.AuthHeader;
import hps.fnd.dto.AuthLine;
import hps.fnd.dto.HpsResource;
import hps.fnd.dto.HpsResourceItem;
import hps.fnd.mapper.AuthHeaderMapper;
import hps.fnd.mapper.AuthLineMapper;
import hps.fnd.mapper.HpsResourceItemMapper;
import hps.fnd.mapper.HpsResourceMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

/**
 * @name AuthCache
 * @description redis缓存处理
 * @author hongan.dong@hand-china.com 2016年9月13日下午2:51:34
 * @version 1.0
 */
@FreeMarkerBean
@Component
public class AuthCache extends HashStringRedisCache<List<AuthLine>> {
	@Autowired
	private AuthHeaderMapper authHeaderMapper;
	@Autowired
	private AuthLineMapper authLineMapper;
	@Autowired
	private HpsResourceMapper resourceMapper;
	@Autowired
	private HpsResourceItemMapper resourceItemMapper;
	@Autowired
	RedisConnectionFactory redisConnectionFactory;

	@Override
	public void init() {
		setType(Map.class);
		strSerializer = getRedisTemplate().getStringSerializer();
		initLoad();
	}

	@Override
	public List<AuthLine> getValue(String key) {
		return (List<AuthLine>) super.getValue(key);
	}

	@Override
	public void setValue(String key, List<AuthLine> list) {
		super.setValue(key, (List<AuthLine>) list);
	}

	@Override
	public void remove(String key) {
		super.remove(key);
	}

	@Override
	protected void initLoad() {
		List<AuthHeader> headersList = authHeaderMapper.redisInitHeaders();
		List<AuthLine> linesList = new ArrayList<AuthLine>();
		for (int i = 0; i < headersList.size(); i++) {
			linesList = authLineMapper.redisInitLines(headersList.get(i).getAuthHeaderId());
			if (linesList.isEmpty()) {
				continue;
			}
			setValue(linesList.get(0).getRoleId() + "." + linesList.get(0).getResourceId(), linesList);
		}
	}

	public void reLoad(Long authHeaderId) {
		List<AuthLine> linesList = authLineMapper.redisInitLines(authHeaderId);
		setValue(linesList.get(0).getRoleId() + "." + linesList.get(0).getResourceId(), linesList);
	}

	public void remove(String key, Long authHeaderId) {
		List<AuthLine> linesList = authLineMapper.redisInitLines(authHeaderId);
		if (linesList.isEmpty()) {
			remove(key);
		} else {
			setValue(key, linesList);
		}
	}

	protected byte[] getKey(String category, String cacheName) {
		return strSerializer.serialize(new StringBuilder(category).append(":").append(cacheName).toString());
	}

	protected byte[] getField(String key) {
		return strSerializer.serialize(key);
	}

	/**
	 * @description 从redis取值
	 * @param authLine
	 *            DTO
	 * @return List<AuthLine>
	 */
	public String getAuthValue(AuthLine authLine) {
		Long roleId = authLine.getRoleId();
		Long resourceId = authLine.getResourceId();
		byte[] key = getKey("hap:cache", "authority");
		StringBuilder str = new StringBuilder();
		str.append(roleId);
		str.append(".");
		str.append(resourceId);
		byte[] field = getField(str.toString());
		byte[] bytes = redisConnectionFactory.getConnection().hGet(key, field);
		String jsonString = strSerializer.deserialize(bytes);
		return jsonString;
	}

	/**
	 * @description 根据url查询资源ID
	 * @param url
	 * @return 资源ID
	 */
	public Long selectResourceId(String url) {
		HpsResource hpsResource = new HpsResource();
		hpsResource.setResourceUrl(url);
		List<HpsResource> resourceList = resourceMapper.selectResource(hpsResource);
		if (resourceList.size() != 0) {
			return resourceList.get(0).getResourceId();
		}
		return null;
	}

	/**
	 * @description 资源启用标识
	 * @param resourceId
	 *            资源 ID
	 * @return 启用标识
	 */
	public String enableFlagResources(Long resourceId) {
		HpsResource hpsResource = new HpsResource();
		hpsResource.setResourceId(resourceId);
		HpsResource hpsResource2 = resourceMapper.selectEnabledFlag(hpsResource);
		if (hpsResource2 != null) {
			return hpsResource2.getEnabledFlag();
		}
		return null;
	}

	/**
	 * @description 资源管理中组件启用标识
	 * @param resourceId
	 *            资源ID
	 * @param itemCode
	 *            组件代码
	 * @return 启用标识
	 */
	public String enableFlagItems(Long resourceId, String itemCode, String itemRegion) {
		HpsResourceItem hpsResourceItem = resourceItemMapper.selectEnableFlag(resourceId, itemCode, itemRegion);
		if (hpsResourceItem != null) {
			return hpsResourceItem.getEnabledFlag();
		}
		return null;
	}

	/**
	 * @description 角色权限头表启用标识
	 * @param roleId
	 *            角色ID
	 * @param resourceId
	 *            资源ID
	 * @return 启用标识
	 */
	public String enableFlagAuthHeaders(String roleId, Long resourceId) {
		AuthHeader authHeader = new AuthHeader();
		authHeader.setRoleId(Long.parseLong(roleId));
		authHeader.setResourceId(resourceId);
		AuthHeader authHeader2 = authHeaderMapper.selectAuthHeader(authHeader);
		if (authHeader2 != null) {
			return authHeader2.getEnabledFlag();
		}
		return null;
	}

}
