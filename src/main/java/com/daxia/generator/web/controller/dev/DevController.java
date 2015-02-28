package com.daxia.generator.web.controller.dev;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.daxia.generator.config.AppProperties;
import com.daxia.generator.model.Authority;
import com.daxia.generator.model.AuthorityDAO;
import com.daxia.generator.model.Menu;
import com.daxia.generator.model.MenuDAO;
import com.daxia.generator.model.RoleAuthority;
import com.daxia.generator.model.RoleAuthorityDAO;
import com.daxia.generator.util.dev.JdbcUtils;
import com.daxia.generator.util.dev.ModelInfo;
import com.daxia.generator.util.k.GenerateType_K;
import com.daxia.generator.util.k.TemplateUtils_K;
import com.google.common.collect.Lists;

@Controller
@RequestMapping(value = "/dev", produces="text/html;charset=UTF-8") // produces是为了解决@ResponseBody返回乱码
public class DevController {
	
	// key: dbName
	private Map<String, BasicDataSource> dsMap = new HashMap<String, BasicDataSource>();
	
	@RequestMapping("index")
	public String index(HttpServletRequest request, Map<String, Object> map) {
		/*Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				map.put(cookie.getName(), cookie.getValue());
	        }
		}
				*/
		return "dev/dev_index";
	}
	
	@ResponseBody
    @RequestMapping(value = "/generate") 
	public String generate(
		String model,
	    String modelChineseName,                   
	    String parentMenu,                         
	    String menu,                               
	    String db,
	    String dbUsername,
	    String dbPassword,
	    String table,
	    String menuTable,
	    String authorityTable,
	    String roleAuthorityTable,
	    String templateType,
	    String generateContents, // 逗号隔开
	    @RequestParam("types[]") String[] types,          
	    @RequestParam("names[]") String[] names,
	    @RequestParam("dbNames[]") String[] dbNames,
	    @RequestParam("comments[]") String[] comments,
	    @RequestParam("shortComments[]") String[] shortComments,
	    @RequestParam("asQueries[]") boolean[] asQueries,    
	    @RequestParam("asLikeQueries[]") boolean[] asLikeQueries,
	    @RequestParam("dateformats[]") String[] dateformats,
	    @RequestParam("queryTypes[]") String[] queryTypes,
	    @RequestParam("modelDisplayFields[]") String[] modelDisplayFields,
	    
	    HttpServletRequest request,
	    HttpServletResponse response,
	    String projectPath, // 项目路径
	    String basePackage // 基本包名，如com.daxia.xxx
	    ) throws Exception {

        /*  // cookie
            Cookie cookie = new Cookie("projectPath", projectPath);
            cookie.setMaxAge(Integer.MAX_VALUE);
            cookie.setPath("/");
            response.addCookie(cookie);
            cookie = new Cookie("basePackage", basePackage);
            cookie.setMaxAge(Integer.MAX_VALUE);
            cookie.setPath("/");
            response.addCookie(cookie);*/
            
            
            String _model = model.substring(0, 1).toLowerCase() + model.substring(1);
            String menuUrl = "admin/" + _model + "/list";
            String authorityCode = _model;
            
            ModelInfo mf = new ModelInfo();
            mf.setModel(model);
            mf.setModelChineseName(modelChineseName);
            mf.setParentMenu(parentMenu);
            mf.setMenu(menu);
            mf.setMenuUrl(menuUrl);
            mf.setAuthorityCode(authorityCode);
            mf.setTypes(types);
            mf.setNames(names);
            mf.setDbNames(dbNames);
            mf.setShortComments(shortComments);
            mf.setComments(comments);
            mf.setAsQueries(asQueries);
            mf.setAsLikeQueries(asLikeQueries);
            mf.setDateFormats(dateformats);
            mf.setQueryTypes(queryTypes);
            mf.setModelDisplayFields(modelDisplayFields);

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("mf", mf);
            map.put("basePackage", basePackage);
            map.put("model", _model);
            map.put("Model", model);
            map.put("table", table.toLowerCase());

            List<String> shortTypes = Lists.newArrayList();
            Set<String> set = new HashSet<String>();
            for (String t : types) {
                shortTypes.add(t.substring(t.lastIndexOf(".") + 1));
                if (!t.startsWith("java.lang.")) {
                    set.add(t);
                }
                if (t.equals("java.util.Date")) {
                    set.add("org.springframework.format.annotation.DateTimeFormat");
                }
                if (t.contains(basePackage)) {
                    set.add("javax.persistence.JoinColumn");
                    set.add("javax.persistence.ManyToOne");
                }
            }
            map.put("shortTypes", shortTypes);
            map.put("imports", set);
            
            List<String> upperNames = Lists.newArrayList();
            for (String n : names) {
                upperNames.add(n.substring(0, 1).toUpperCase() + n.substring(1));
            }
            map.put("upperNames", upperNames);
            map.put("templateType", templateType);
            
            List<GenerateType_K> generateTypes = Lists.newArrayList();
            
            List<String> generateList = Arrays.asList(generateContents.split(","));
            
            if (generateList.contains("model")) {
                generateTypes.add(GenerateType_K.Model);
            }
            if (generateList.contains("dao")) {
                generateTypes.add(GenerateType_K.ModelDAO);
            }
            if (generateList.contains("service")) {
                generateTypes.add(GenerateType_K.ModelService);
            }
            if (generateList.contains("controller")) {
                generateTypes.add(GenerateType_K.AdminModelController);
            }
            if (generateList.contains("list")) {
                generateTypes.add(GenerateType_K.model_list);
            }
            if (generateList.contains("detail")) {
                generateTypes.add(GenerateType_K.model_detail);
            }
            // generateTypes.add(GenerateType_K.model_search);
            
            TemplateUtils_K.generate(generateTypes.toArray(new GenerateType_K[] {}), map, projectPath);

            Connection connection = getConnection(db, dbUsername, dbPassword);
            JdbcUtils.connection = connection;
            
            AuthorityDAO authorityDAO = new AuthorityDAO(authorityTable);
            RoleAuthorityDAO roleAuthorityDAO = new RoleAuthorityDAO(roleAuthorityTable);
            MenuDAO menuDAO = new MenuDAO(menuTable);
            
            Long roleId = 1L;
            String auName = modelChineseName + "管理";
            Authority au = authorityDAO.findByName(auName);
            if (au == null) {
                au = new Authority();
                au.setName(auName);
                au.setCode(authorityCode);
                authorityDAO.create(au);
                long id = authorityDAO.findByName(auName).getId();
                RoleAuthority ra = new RoleAuthority(roleId, id);
                roleAuthorityDAO.create(ra);
            }
            long parentAuthorityId = authorityDAO.findByName(auName).getId();
            // 新增， 修改， 删除
            auName = "查看" + modelChineseName;
            au = authorityDAO.findByName(auName);
            if (au == null) {
                au = new Authority();
                au.setName(auName);
                au.setCode(authorityCode + ".list");
                au.setParent_authority_id(parentAuthorityId);
                authorityDAO.create(au);
                long id = authorityDAO.findByName(auName).getId();
                RoleAuthority ra = new RoleAuthority(roleId, id);
                roleAuthorityDAO.create(ra);
            }
            auName = "新增" + modelChineseName;
            au = authorityDAO.findByName(auName);
            if (au == null) {
                au = new Authority();
                au.setName(auName);
                au.setCode(authorityCode + ".add");
                au.setParent_authority_id(parentAuthorityId);
                authorityDAO.create(au);
                long id = authorityDAO.findByName(auName).getId();
                RoleAuthority ra = new RoleAuthority(roleId, id);
                roleAuthorityDAO.create(ra);
            }
            auName = "修改" + modelChineseName;
            au = authorityDAO.findByName(auName);
            if (au == null) {
                au = new Authority();
                au.setName(auName);
                au.setCode(authorityCode + ".update");
                au.setParent_authority_id(parentAuthorityId);
                authorityDAO.create(au);
                long id = authorityDAO.findByName(auName).getId();
                RoleAuthority ra = new RoleAuthority(roleId, id);
                roleAuthorityDAO.create(ra);
            }
            auName = "删除" + modelChineseName;
            au = authorityDAO.findByName(auName);
            if (au == null) {
                au = new Authority();
                au.setName(auName);
                au.setCode(authorityCode + ".delete");
                au.setParent_authority_id(parentAuthorityId);
                authorityDAO.create(au);
                long id = authorityDAO.findByName(auName).getId();
                RoleAuthority ra = new RoleAuthority(roleId, id);
                roleAuthorityDAO.create(ra);
            }
            
            // 菜单
            Menu parent = menuDAO.findByName(parentMenu);
            long parentMenuId = parent.getId();
            Menu m = menuDAO.findByName(modelChineseName + "管理");
            if (m == null) {
                m = new Menu();
                m.setLevel(2);
                m.setParent_id(parentMenuId);
                m.setHref(menuUrl);
                m.setName(modelChineseName + "管理");
                m.setAuthority_id(parentAuthorityId);
                menuDAO.create(m);
            }
            
            // 角色表
            
            /*AuthorityDTO aDto = new AuthorityDTO();
            aDto.setCode(authorityCode);
            Authority oldListAuthority = authorityService.uniqueFind(aDto);
            if (oldListAuthority == null) {
                // 新建权限
                
                AuthorityDTO mAuthority = new AuthorityDTO();
                mAuthority.setCode(authorityCode);
                mAuthority.setName(modelChineseName + "管理");
                authorityService.save(mAuthority);
                aDto.setCode(mAuthority.getCode());
                mAuthority = authorityService.uniqueFind(aDto);
                
                RoleDTO roleDTO = new RoleDTO();
                roleDTO.setName("管理员");
                RoleDTO role = roleService.uniqueFind(roleDTO);
                role.getAuthorities().add(mAuthority);
                
                AuthorityDTO listAuthority = new AuthorityDTO();
                listAuthority.setParentAuthority(mAuthority);
                listAuthority.setCode(mAuthority.getCode() + ".list");
                listAuthority.setName("查看" + modelChineseName);
                authorityService.save(listAuthority);
                
                aDto.setCode(listAuthority.getCode());
                listAuthority = authorityService.uniqueFind(aDto);
                role.getAuthorities().add(listAuthority);
                
                AuthorityDTO addAuthority = new AuthorityDTO();
                addAuthority.setParentAuthority(mAuthority);
                addAuthority.setCode(mAuthority.getCode() + ".add");
                addAuthority.setName("新增" + modelChineseName);
                authorityService.save(addAuthority);
                
                aDto.setCode(addAuthority.getCode());
                addAuthority = authorityService.uniqueFind(aDto);
                role.getAuthorities().add(addAuthority);
                
                AuthorityDTO updateAuthority = new AuthorityDTO();
                updateAuthority.setParentAuthority(mAuthority);
                updateAuthority.setCode(mAuthority.getCode() + ".update");
                updateAuthority.setName("修改" + modelChineseName);
                authorityService.save(updateAuthority);
                
                aDto.setCode(updateAuthority.getCode());
                updateAuthority = authorityService.uniqueFind(aDto);
                role.getAuthorities().add(updateAuthority);
                
                AuthorityDTO deleteAuthority = new AuthorityDTO();
                deleteAuthority.setParentAuthority(mAuthority);
                deleteAuthority.setCode(mAuthority.getCode() + ".delete");
                deleteAuthority.setName("删除" + modelChineseName);
                authorityService.save(deleteAuthority);
                
                aDto.setCode(deleteAuthority.getCode());
                deleteAuthority = authorityService.uniqueFind(aDto);
                role.getAuthorities().add(deleteAuthority);
                
                roleService.updateAllField(role);
            }
            
            MenuDTO menuDTO = new MenuDTO();
            menuDTO.setHref("admin/" + _model + "/list");
            Menu oldMenu = menuService.findOne(menuDTO);
            if (oldMenu == null) {
                MenuDTO pDto = new MenuDTO();
                pDto.setName(parentMenu);
                Menu pMenu = menuService.findOne(pDto);
                // 生成菜单
                MenuDTO newMenu = new MenuDTO();
                newMenu.setParent(pMenu);
                newMenu.setHref(menuUrl);
                newMenu.setLevel(2);

                aDto.setCode(_model + ".list");
                AuthorityDTO listAuthority = authorityService.uniqueFind(aDto);
                newMenu.setAuthority(listAuthority);
                newMenu.setName(modelChineseName + "管理");
                menuService.save(newMenu);
            }*/
            
            connection.close();
            return null;
    		
    }

    private Connection getConnection(String db, String dbUsername, String dbPassword) throws SQLException {
        BasicDataSource ds = dsMap.get(db);
        if (ds == null) {
            initDataSource(db, dbUsername, dbPassword);
        }
        return dsMap.get(db).getConnection();
    }
	
	/*@ResponseBody
    @RequestMapping(value = "/findParentMenu")
    public String findParentMenu(HttpServletResponse response, Integer level) throws IOException {
        MenuDTO dto = new MenuDTO();
        dto.setLevel(level - 1);
        List<MenuDTO> menus = menuService.list(dto, null);
        return JSONArray.toJSONString(menus);
    }*/
	
    @RequestMapping("login")
    public String login() throws Exception {
	    return "dev/dev_login";
    }

    private boolean logined = false;

	/*@ResponseBody
    @RequestMapping(value = "/findParentAuthority")
    public String findParentAuthority(HttpServletResponse response) throws IOException {
        List<AuthorityDTO> authorities = authorityService.list(new AuthorityDTO(), null);
        return JSONArray.toJSONString(authorities);
    }*/
	
	@ResponseBody
    @RequestMapping(value = "/getTableInfo")
    public String getTableInfo(String dbName, String dbUsername, String dbPassword, String table) throws Exception {
		Connection conn = getConnection(dbName, dbUsername, dbPassword, table);
		String sql = "select column_name, data_type, column_comment from `information_schema`.`COLUMNS` where TABLE_SCHEMA =  ? AND table_name = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, dbName.toLowerCase());
		stmt.setString(2, table.toLowerCase());
		ResultSet rs = stmt.executeQuery();
		List<ModelField> fieldList = new ArrayList<ModelField>();
		while (rs.next()) {
			ModelField mf = new ModelField();
			mf.name = rs.getString("column_name");
			mf.comment = rs.getString("column_comment");
			String dataType = rs.getString("data_type");
			mf.type = mysqlJavaDataTypeMapping.get(dataType);
			fieldList.add(mf);
		}
		if (CollectionUtils.isEmpty(fieldList)) {
			throw new RuntimeException("数据库表" + dbName + "." + table + "不存在");
		}
		
		conn.close();
        return JSONArray.toJSONString(fieldList);
    }

    private Connection getConnection(String dbName, String dbUsername, String dbPassword, String table) {
    	DataSource ds = dsMap.get(dbName.toLowerCase());
    	if (ds == null) {
    		initDataSource(dbName, dbUsername, dbPassword);
    		ds = dsMap.get(dbName.toLowerCase());
    	}
    	try {
	        return ds.getConnection();
        } catch (SQLException e) {
        	throw new RuntimeException(e.getMessage(), e);
        }
    }

	private void initDataSource(String dbName, String dbUsername, String dbPassword) {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/" + dbName + "?useUnicode=true&characterEncoding=utf8");
		ds.setUsername(dbUsername);
		ds.setPassword(dbPassword);
		dsMap.put(dbName.toLowerCase(), ds);
    }

	@RequestMapping(value = "query", method = RequestMethod.GET)
    public String query() throws Exception {
	    return "dev/dev_query";
    }
    
    @RequestMapping(value = "query", method = RequestMethod.POST)
    public String querySubmit(String sql, Map<String, Object> map) throws Exception {
    	Connection connection = null; //dataSource.getConnection();
    	map.put("result", JdbcUtils.query(connection, sql));
    	map.put("sql", sql);
	    return "dev/dev_query";
    }
	
	private static Map<String, Class<? extends Object>> mysqlJavaDataTypeMapping = new HashMap<String, Class<? extends Object>>();
	static {
		mysqlJavaDataTypeMapping.put("bigint", Long.class);
		mysqlJavaDataTypeMapping.put("varchar", String.class);
		mysqlJavaDataTypeMapping.put("text", String.class);
		mysqlJavaDataTypeMapping.put("char", String.class);
		mysqlJavaDataTypeMapping.put("int", Integer.class);
		mysqlJavaDataTypeMapping.put("smallint", Integer.class);
		mysqlJavaDataTypeMapping.put("tinyint", Byte.class);
		mysqlJavaDataTypeMapping.put("float", Float.class);
		mysqlJavaDataTypeMapping.put("double", Double.class);
		mysqlJavaDataTypeMapping.put("blob", byte[].class);
		mysqlJavaDataTypeMapping.put("bool", boolean.class);
		mysqlJavaDataTypeMapping.put("boolean", boolean.class);
		mysqlJavaDataTypeMapping.put("datetime", Date.class);
		mysqlJavaDataTypeMapping.put("date", Date.class);
		mysqlJavaDataTypeMapping.put("timestamp", Timestamp.class);
	}
	
	private static String db = AppProperties.get("project.name");
	
}
