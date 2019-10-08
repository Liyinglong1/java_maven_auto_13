package com.lemonban.base.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.dev.ReSave;

import com.alibaba.fastjson.JSONObject;
import com.lemon.EncryptUtils;
import com.lemonban.base.pojo.ApiCaseDetail;
import com.lemonban.base.pojo.LemonHeader;

public class HttpUtils {

	private static Logger logger = Logger.getLogger(HttpUtils.class);

	/**
	 * post请求
	 * 
	 * @param url
	 *            请求url
	 * @param map
	 *            请求的数据
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static String post(String url, Map<String, String> map) {
		String entityStr = null;
		try {
			HttpPost post = new HttpPost(url);
			// 参数容器
			List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
			// 参数都在map中
			Set<String> keySet = map.keySet();
			for (String key : keySet) {// 每遍历一次都要生成一个名值对
				String name = key;
				String value = map.get(key);
				// 每遍历一次都要生成一个名值对
				BasicNameValuePair nameValuePair = new BasicNameValuePair(name, value);
				parameters.add(nameValuePair);
			}

			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters, "utf-8");
			// 设值请求体
			post.setEntity(formEntity);

			CloseableHttpClient httpClient = HttpClients.createDefault();
			CloseableHttpResponse response = httpClient.execute(post);
			// 3：获取响应体
			HttpEntity respEntity = response.getEntity();
			entityStr = EntityUtils.toString(respEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entityStr;
	}

	/**
	 * post方法
	 * 
	 * @param url
	 *            请求url
	 * @param requestData
	 *            请求体（json字符串）
	 * @return
	 */
	public static String post(ApiCaseDetail apiCaseDetail) {
		String entityStr = null;
		try {
			HttpPost post = new HttpPost(apiCaseDetail.getApiInfo().getUrl());
			// 拿到所有的headers
			String headersStr = apiCaseDetail.getApiInfo().getHeaders();
			List<LemonHeader> headers = JSONObject.parseArray(headersStr, LemonHeader.class);
			// 设置必须的请求头
			for (LemonHeader lemonHeader : headers) {
				// 替换请求头中间的${token}-->替换成登录信息中拿到的token
				String replacedValue = ParamUtils.getReplacedStr(lemonHeader.getValue());
				// 设置请求头
				post.setHeader(lemonHeader.getName(), replacedValue);
			}
			// 创建一个请求体
			StringEntity entity = new StringEntity(apiCaseDetail.getRequestData(), ContentType.APPLICATION_JSON);
			// 设置字符集
			entity.setContentEncoding("utf-8");
			// 设值请求体
			post.setEntity(entity);
			// 创建发包客户端
			CloseableHttpClient httpClient = HttpClients.createDefault();
			// 得到响应
			CloseableHttpResponse response = httpClient.execute(post);
			// 获取响应体
			HttpEntity respEntity = response.getEntity();
			entityStr = EntityUtils.toString(respEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entityStr;
	}

	/**
	 * post方法
	 * 
	 * @param apiCaseDetail
	 *            接口测试用例详细信息
	 * @return
	 */
	// public static String post(ApiCaseDetail apiCaseDetail) {
	// return post(apiCaseDetail);
	// }

	/**
	 * get
	 * 
	 * @param url
	 *            请求url
	 * @param map
	 *            参数
	 * @return
	 */
	public static String get(String url, Map<String, String> map) {
		try {
			// get请求的参数要拼接
			List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();

			Set<String> keySet = map.keySet();
			for (String key : keySet) {// 每遍历一次都要生成一个名值对
				String name = key;
				String value = map.get(key);
				// 每遍历一次都要生成一个名值对
				BasicNameValuePair nameValuePair = new BasicNameValuePair(name, value);
				parameters.add(nameValuePair);
			}

			String params = URLEncodedUtils.format(parameters, "utf-8");

			// 准备好一个get请求
			HttpGet get = new HttpGet(url + "?" + params);

			// 准备一个http发包客户端
			CloseableHttpClient httpClient = HttpClients.createDefault();
			// 发包,得到http响应
			CloseableHttpResponse response = httpClient.execute(get);

			// 3：获取响应体
			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * get
	 * 
	 * @param url
	 *            请求url
	 * @param map
	 *            参数
	 * @return
	 */
	public static String get(ApiCaseDetail apiCaseDetail) {
		try {
			// get请求的参数要拼接
			List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
			// 把json对象转成map对象
			Map<String, Object> map = JSONObject.parseObject(apiCaseDetail.getRequestData());
			Set<String> keySet = map.keySet();
			for (String key : keySet) {// 每遍历一次都要生成一个名值对
				String name = key;
				String value = map.get(key).toString();
				// 每遍历一次都要生成一个名值对
				BasicNameValuePair nameValuePair = new BasicNameValuePair(name, value);
				parameters.add(nameValuePair);
			}
			String params = URLEncodedUtils.format(parameters, "utf-8");
			// 准备好一个get请求
			HttpGet get = new HttpGet(apiCaseDetail.getApiInfo().getUrl() + "?" + params);// http://domian/path?xx=yy&aa=bb&cc=dd
			// 设置必须的请求头
			// get.setHeader("X-Lemonban-Media-Type", "lemonban.v1");
			String headersStr = apiCaseDetail.getApiInfo().getHeaders();
			List<LemonHeader> headers = JSONObject.parseArray(headersStr, LemonHeader.class);
			for (LemonHeader lemonHeader : headers) {
				get.setHeader(lemonHeader.getName(), lemonHeader.getValue());
			}
			// 准备一个http发包客户端
			CloseableHttpClient httpClient = HttpClients.createDefault();
			// 发包,得到http响应
			CloseableHttpResponse response = httpClient.execute(get);

			// 3：获取响应体
			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String delete(ApiCaseDetail apiCaseDetail) {
		// TODO 编写delete方法的具体的逻辑
		return null;
	}

	private static String patch(ApiCaseDetail apiCaseDetail) {
		// FIXME 编写patch方法的具体的逻辑
		return null;
	}

	public static String delete(String url, Map<String, String> map) throws Exception {
		// HttpDelete
		return null;
	}

	public static String put(String url, Map<String, String> map) throws Exception {
		// HttpPut
		return null;
	}

	public static String patch(String url, Map<String, String> map) throws Exception {
		// HttpPatch
		return null;
	}

	/**
	 * 执行测试用例
	 * 
	 * @param apiCaseDetail
	 *            测试用例详细信息对象 GET/POST字符串硬编码：常量、枚举
	 * @return
	 */
	public static String excute(ApiCaseDetail apiCaseDetail) {

		// 处理请求体数据：普通参数的替换、鉴权的参数的添加
		handleRequestData(apiCaseDetail);

		// 请求方法
		String type = apiCaseDetail.getApiInfo().getType();
		logger.info("开始发包：" + apiCaseDetail.getCaseId());
		// 请求的分发
		String result = null;
		if ("GET".equalsIgnoreCase(type)) {
			result = HttpUtils.get(apiCaseDetail);
		} else if ("POST".equalsIgnoreCase(type)) {
			result = HttpUtils.post(apiCaseDetail);
		} else if ("PATCH".equalsIgnoreCase(type)) {
			result = HttpUtils.patch(apiCaseDetail);
		} else if ("DELETE".equalsIgnoreCase(type)) {
			result = HttpUtils.delete(apiCaseDetail);
		}
		logger.info("测试用例执行结果为：" + result);
		return result;
	}

	private static void handleRequestData(ApiCaseDetail apiCaseDetail) {
		// 原始的请求体：{"member_id": ${member_id},"amount": -100}
		String requestData = apiCaseDetail.getRequestData();
		// 先对请求体数据替换：{"member_id": 1,"amount": -100}
		String replacedRequestData = ParamUtils.getReplacedStr(requestData);
		// -------------------------------------------------
		// 是否需要鉴权
		String auth = apiCaseDetail.getApiInfo().getAuth();
		if (auth != null && "T".equalsIgnoreCase(auth)) {
			// 获取到的token--》从全局数据池拿token
			String token = ParamUtils.getGlobalData("token").toString();
			// 获取到的时间戳
			Long timestamp = System.currentTimeMillis() / 1000;
			// token的钱50位+timestamp
			String tempStr = token.substring(0, 50) + timestamp;
			// RSA非对称加密得到签名sign
			String sign = EncryptUtils.rsaEncrypt(tempStr);
			// 将请求体转成map
			Map<String, Object> reqStrMap = (Map<String, Object>) JSONObject.parse(replacedRequestData);
			// sign+timestamp-->请求体
			reqStrMap.put("timestamp", timestamp);
			reqStrMap.put("sign", sign);
			replacedRequestData = JSONObject.toJSONString(reqStrMap);
		}
		// --------------------------------------------------
		// 重新设置回测试用例对象
		apiCaseDetail.setRequestData(replacedRequestData);
	}

	public static void main(String[] args) {
		// String result = EncryptUtils.rsaEncrypt("123456");
		// 请求体
		String reqStr = "{\"member_id\": 1,\"amount\": 0}";
		// String reqStr2 = "{'member_id': 1,'amount':
		// 0,'timestamp':1569035331,'sign':'xxxxx'}";
		// 获取到的token--》请求头
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJtZW1iZXJfaWQiOjExLCJleHAiOjE1NjkwMzI4Mzl9.MbDsm9F0vnbIfsDKWn5ihWc1VbLwrpbFpsfLgKcGErYdQ3GShr2JN6K99kdmiCGenwfHXoE3Au8DJ_j4QYYKrg";
		// 获取到的时间戳
		Long timestamp = System.currentTimeMillis() / 1000;
		// token的钱50位+timestamp
		String tempStr = token.substring(0, 50) + timestamp;
		// RSA非对称加密得到签名sign
		String sign = EncryptUtils.rsaEncrypt(tempStr);
		// 将请求体转成map
		Map<String, Object> map = (Map<String, Object>) JSONObject.parse(reqStr);
		// sign+timestamp-->请求体
		map.put("timestamp", timestamp);
		map.put("sign", sign);

		String finalReqStr = JSONObject.toJSONString(map);

		System.out.println(timestamp);
		System.out.println(token);
		System.out.println(tempStr);
		System.out.println(sign);
		// System.out.println(map.toString());
		System.out.println(finalReqStr);
	}

}
