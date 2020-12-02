package com.slow.college.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.slow.college.exception.ScBizException;
import com.slow.college.mapper.StudentMapper;
import com.slow.college.mapper.TrainingImageMapper;
import com.slow.college.mapper.TrainingMapper;
import com.slow.college.mapper.TrainingTaskMapper;
import com.slow.college.model.Student;
import com.slow.college.model.Training;
import com.slow.college.param.response.BaseRsp;
import com.slow.college.param.scenum.RspEnum;
import com.slow.college.param.training.CheckTraningItem;
import com.slow.college.param.training.TraniningResultItem;
import com.slow.college.param.training.WaitTrainingItem;
import com.slow.college.param.user.ClassStudentItem;
import com.slow.college.param.user.StudentClassItem;
import com.slow.college.param.user.StudentTrainingsItem;
import com.slow.college.param.user.UserLoginItem;
import com.slow.college.request.TrainingReq;
import com.slow.college.request.UserReq;
import com.slow.college.response.ObjectResponse;
import com.slow.college.response.ResponseCode;
import com.slow.college.service.UserService;
import com.slow.college.utils.DateUtil;
import com.slow.college.utils.EncryptUtil;

@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger log = LogManager.getLogger(UserServiceImpl.class);
	
	@Autowired
	private StudentMapper studentMapper;
	
	@Autowired
	private TrainingTaskMapper trainingTaskMapper;
	
	@Autowired
	private TrainingImageMapper trainingImageMapper;
	
	@Autowired
	private TrainingMapper trainingMapper;
	
	@Override
	public BaseRsp login (UserReq req) throws ScBizException {
		ObjectResponse<UserLoginItem> response = new ObjectResponse<>();
		if (req.getMobile() == null || req.getMobile().trim().length() == 0 || 
			req.getPassword() == null || req.getPassword().trim().length() == 0) {
			return BaseRsp.fail(RspEnum.PARAM_LEAK);
		}
		Student s = studentMapper.searchStudentByPhone(req.getMobile().trim());
		if (s == null) {
			return BaseRsp.fail(RspEnum.USER_UNEXISTS);
		}
		if (s.getPassword() == null || 
			!req.getPassword().trim().equals(s.getPassword().trim())) {
			return BaseRsp.fail(RspEnum.USER_PASSWORD);
		}
		long time = System.currentTimeMillis();
		String token = EncryptUtil.GetMD5Code(s.getName().trim() + time + "json");
		s.setToken(token);
		studentMapper.updateStudentById(s.getId(), token);
		response.setCode(ResponseCode.CODE_1);
		response.setMessage(ResponseCode.MSG_1);
		UserLoginItem res = new UserLoginItem();
		res.setToken(token);
		res.setId(s.getId());
		res.setName(s.getName());
    	List<StudentClassItem> sL = trainingTaskMapper
			.searchStudentSourceByIds(s.getId() + "");
    	if (sL != null && sL.size() == 1 && sL.get(0) != null) {
    		res.setTrainingDays(sL.get(0).getTrainingDays());
    	}
		return BaseRsp.success(res);
	}
	
	@Override
	public BaseRsp getClassStudent (UserReq req) throws ScBizException {
		ObjectResponse<ClassStudentItem> response = new ObjectResponse<>();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		long startTime = System.currentTimeMillis();
		if (req.getToken() == null || req.getToken().trim().length() == 0) {
			return BaseRsp.fail(RspEnum.USER_NOTOKEN);
		}
		if (req.getDate() == null || req.getDate().trim().length() == 0) {
			req.setDate(DateUtil.parseString(new Date(), DateUtil.YYYY_MM_DD));
		} else {
			req.setDate(req.getDate().trim());
		}
		Student s = studentMapper.searchStudentByToken(req.getToken().trim());
		if (s == null) {
			return BaseRsp.fail(RspEnum.NO_DATA);
		}
		ClassStudentItem res = studentMapper.searchUserDataItemByToken(s.getToken());
		if (res == null) {
			return BaseRsp.fail(RspEnum.USER_UNEXISTS);
		}
		res.setDate(req.getDate());
		//这块取的总的分数（student表里的分数）
		List<StudentClassItem> sciL = studentMapper.searchUserDataItemByClassId(res.getClassId());
		if (sciL != null && sciL.size() > 0) {
			StringBuilder sb = new StringBuilder();
			for (StudentClassItem item : sciL) {
				sb.append(",").append(item.getId());
			}
			if (sb.length() > 0) {
				//学习时长，分数
				List<StudentClassItem> sL = trainingTaskMapper
					.searchStudentSourceByIds(sb.substring(1));
				//打卡信息
				List<StudentTrainingsItem> stiL = trainingTaskMapper
					.searchStudentTrainingsItemByStudentIds(
					sb.substring(1), req.getDate());
				//打卡图片
				List<StudentTrainingsItem> stiL1 = trainingTaskMapper
					.searchStudentTrainingsItemImgByStudentIds(
					sb.substring(1), req.getDate());
				if (sL != null && sL.size() > 0) {
					for (StudentClassItem item : sciL) {
						for (StudentClassItem items : sL) {
							if (item.getId().intValue() == items.getId().intValue()) {
								item.setTrainingDays(items.getTrainingDays());
								item.setScore(items.getScore());
							}
						}
					}
				}
				if (stiL != null && stiL.size() > 0 && 
					stiL1 != null && stiL1.size() > 0) {
					for (StudentTrainingsItem item : stiL) {
						if (item.getTrainingId() != null) {
							List<String> imgL = new ArrayList<>();
							for (StudentTrainingsItem item1 : stiL1) {
								if (item1 != null && 
									item1.getTrainingId() != null && 
									item.getTrainingId().intValue() == 
									item1.getTrainingId().intValue()) {
									imgL.add(item1.getVideoUrl());
								}
							}
							item.setImgList(imgL);
						}
					}
				}
				if (stiL != null && stiL.size() > 0) {
					for (StudentClassItem item : sciL) {
						List<StudentTrainingsItem> l = new ArrayList<>();
						for (StudentTrainingsItem items : stiL) {
							if (item.getId().intValue() == 
								items.getStudentId().intValue()) {
								if (items.getState() != null) {
									switch (items.getState().intValue()) {
										case 0:
											items.setStateDesc("未完成");
											break;
										case 1:
											items.setStateDesc("已完成");
											break;
										case 2:
											items.setStateDesc("未达标");
											break;
									default:
										break;
									}
								} else {
									items.setState((byte) 0);
									items.setStateDesc("未完成");
								}
								l.add(items);
							}
						}
						item.setTrainings(l);
					}
				}
			}
		}
		res.setStudent(sciL);
		return BaseRsp.success(res);
	}
	
	@Override
	public BaseRsp getStudentTrainingTask (UserReq req) throws ScBizException {
		if (req.getToken() == null || req.getToken().trim().length() == 0) {
			return BaseRsp.fail(RspEnum.USER_NOTOKEN);
		}
		Student s = studentMapper.searchStudentByToken(req.getToken().trim());
		if (s == null) {
			return BaseRsp.fail(RspEnum.USER_UNEXISTS);
		}
		List<WaitTrainingItem> res = new ArrayList<>();
		List<WaitTrainingItem> rL = trainingTaskMapper
			.searchWaitTrainingItemByStudentIdAndTime(
			s.getId(), DateUtil.parseString(new Date(), DateUtil.YYYY_MM_DD));
		if (rL != null && rL.size() > 0) {
			for (WaitTrainingItem item : rL) {
				if (item.getState() == null || 
					item.getState().intValue() == 0 ||
					item.getState().intValue() == 2) {
					if (item.getState() == null || 
						item.getState().intValue() == 0) {
						item.setState((byte) 0);
						item.setStateDesc("未完成");
					} else {
						item.setState((byte) 2);
						item.setStateDesc("未达标");
					}
					res.add(item);
				}
			}
		}
		return BaseRsp.success(res);
	}
	
	@Override
	public BaseRsp submitStudentTraining (TrainingReq req) throws ScBizException {
		if (req.getToken() == null || req.getToken().trim().length() == 0) {
			return BaseRsp.fail(RspEnum.USER_NOTOKEN);
		} 
		Student s = studentMapper.searchStudentByToken(req.getToken().trim());
		if (s == null) {
			return BaseRsp.fail(RspEnum.USER_UNEXISTS);
		}
		if (req.getTrainings() == null || req.getTrainings().trim().length() == 0) {
			return BaseRsp.fail(RspEnum.PARAM_LEAK);
		}
		try {
			JSONArray jsonArray = new JSONArray(req.getTrainings().trim());
			if (jsonArray == null || jsonArray.length() == 0) {
				return BaseRsp.fail(RspEnum.PARAM_LEAK);
			}
			//整理参数
			List<Map<String, Object>> resList = new ArrayList<>();
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonO = jsonArray.getJSONObject(i);
				/*log.info(jsonO);*/
				if (!jsonO.has("id") || jsonO.isNull("id")) {
					return BaseRsp.fail(RspEnum.ERROR_PARAM);
				}
				//判断目标数量并且描述并且图片路径并且视频路径为空则退出接口
				if ((!jsonO.has("achievement") || jsonO.isNull("achievement") || 
					jsonO.getInt("achievement") == 0) &&
					
					(!jsonO.has("imgList") || jsonO.isNull("imgList") || 
					jsonO.getJSONArray("imgList").length() == 0) &&
					
					(!jsonO.has("videoUrl") || jsonO.isNull("videoUrl") || 
					jsonO.getString("videoUrl").trim().length() == 0) && 
					
					(!jsonO.has("desc") || jsonO.isNull("desc") || 
					jsonO.getString("desc").trim().length() == 0)) {
					return BaseRsp.fail(RspEnum.ERROR_PARAM);
				}
				Map<String, Object> mapItem = new HashMap<>();
				mapItem.put("id", jsonO.getLong("id"));
				if (jsonO.has("achievement") && !jsonO.isNull("achievement") && jsonO.getInt("achievement") > 0) {
					mapItem.put("achievement", jsonO.getInt("achievement"));
				}
				if (jsonO.has("imgList") && !jsonO.isNull("imgList") && jsonO.getJSONArray("imgList").length() > 0) {
					mapItem.put("imgList", jsonO.getJSONArray("imgList"));
				}
				if (jsonO.has("videoUrl") && !jsonO.isNull("videoUrl") && jsonO.getString("videoUrl").trim().length() > 0) {
					mapItem.put("video", jsonO.getString("videoUrl"));
				}
				if (jsonO.has("desc") && !jsonO.isNull("desc") && jsonO.getString("desc").trim().length() > 0) {
					mapItem.put("desc", jsonO.getString("desc"));
				}
				resList.add(mapItem);
			}
			int addScore = 0;
			//查询用户目标训练任务，包含查询日期已提交数据
			List<CheckTraningItem> checkL = trainingTaskMapper
				.searchCheckTraningItemByStudentIdAndTime(
				s.getId(), DateUtil.parseString(new Date(), DateUtil.YYYY_MM_DD));
			List<TraniningResultItem> res = new ArrayList<>();
			for (Map<String, Object> item : resList) {
				//数据库下，当前用户训练任务及当天已提交数据查询并初始化
				CheckTraningItem cti = null;
				if (checkL != null && checkL.size() > 0) {
					for (CheckTraningItem checkItem : checkL) {
						if ((item.get("id") + "").equals(checkItem.getTaskId() + "")) {
							cti = checkItem;
							break;
						}
					}
				}
				if (cti == null) {
					return BaseRsp.fail(RspEnum.ERROR_DATA);
				}
				if (cti.getAchievement() == null) cti.setAchievement(0);
				if (cti.getDayTrainingtaskId() != null && 
					cti.getDayTrainingtaskId().intValue() > 0) {
					if (cti.getDone().intValue() == 1) {
						addScore--;
					}
					if (!item.containsKey("achievement")) {
						cti.setAchievement(cti.getTarget());
					} else {
						cti.setAchievement(
							//cti.getAchievement() + 
							(int) item.get("achievement"));
					}
					if (cti.getAchievement().intValue() >= cti.getTarget().intValue()) {
						cti.setDone((byte) 1);
						cti.setScore(1);
						addScore++;
					} else {
						cti.setDone((byte) 2);
					}
					if (item.containsKey("videoUrl")) {
						cti.setVideo(item.get("videoUrl") + "");
					}
					if (item.containsKey("desc")) {
						cti.setDesc(item.get("desc") + "");
					}
					if (item.containsKey("imgList")) {
						JSONArray ja = (JSONArray) item.get("imgList");
						List<String> lImg = new ArrayList<>();
						for (int i = 0; i < ja.length(); i++) {
							if (ja.getString(i) != null && ja.getString(i).length() > 0) {
								lImg.add(ja.getString(i));
								trainingImageMapper.insertTrainingImage(
									ja.getString(i), cti.getDayTrainingtaskId());
							}
						}
						/*trainingImageMapper.insertList(lImg, cti.getDayTrainingtaskId());*/
					}
					trainingMapper.updateTraining(
						DateUtil.parseString(new Date(), DateUtil.YYYY_MM_DD), 
						cti.getDone(), 
						cti.getAchievement(), 
						cti.getScore(), 
						cti.getDesc() != null ? cti.getDesc() : null, 
						cti.getVideo() != null ? cti.getVideo() : null,
						cti.getDayTrainingtaskId()
					);
					//整理返回内容
					List<String> imgL = trainingImageMapper
						.searchTrainingImageByTrainingId(
						cti.getTrainingtaskId());
					TraniningResultItem tri = new TraniningResultItem();
					tri.setId(cti.getTaskId());
					tri.setAchievement(cti.getAchievement());
					tri.setDesc(cti.getDesc() != null ? cti.getDesc() : null);
					tri.setImgList(imgL != null && imgL.size() > 0 ? imgL : null);
					tri.setScore(cti.getScore());
					tri.setVideoUrl(cti.getVideo() != null ? cti.getVideo() : null);
					res.add(tri);
				} else {
					if (!item.containsKey("achievement")) {
						cti.setAchievement(cti.getTarget());
					} else {
						cti.setAchievement(
							//cti.getAchievement() + 
							(int) item.get("achievement"));
					}
					Training t = new Training(
						null, cti.getTrainingtaskId(), 
						DateUtil.parseString(new Date(), DateUtil.YYYY_MM_DD), 
						item.containsKey("desc") && item.get("desc") != null && 
							(item.get("desc") + "").length() > 0 ? 
							item.get("desc") + "" : null, 
							cti.getAchievement().intValue() >= cti.getTarget().intValue() ?
							(byte) 1 : (byte) 2, 
						item.containsKey("video") && item.get("video") != null && 
							(item.get("video") + "").length() > 0 ? 
							item.get("video") + "" : null, 
							cti.getAchievement(), cti.getUnit(), 
							cti.getAchievement().intValue() >= cti.getTarget().intValue() ?
							 1 : 0);
					addScore += t.getScore();
					trainingMapper.insertTraining(t);
					if (item.containsKey("imgList")) {
						JSONArray ja = (JSONArray) item.get("imgList");
						List<String> lImg = new ArrayList<>();
						for (int i = 0; i < ja.length(); i++) {
							if (ja.getString(i) != null && ja.getString(i).length() > 0) {
								lImg.add(ja.getString(i));
								trainingImageMapper.insertTrainingImage(ja.getString(i), t.getId());
							}
						}
						/*trainingImageMapper.insertList(lImg, t.getId());*/
					}
					//整理返回参数
					List<String> imgL = trainingImageMapper
						.searchTrainingImageByTrainingId(
						cti.getTrainingtaskId());
					TraniningResultItem tri = new TraniningResultItem();
					tri.setId(cti.getTaskId());
					tri.setAchievement(cti.getAchievement());
					tri.setDesc(cti.getDesc() != null ? cti.getDesc() : null);
					tri.setImgList(imgL != null && imgL.size() > 0 ? imgL : null);
					tri.setScore(cti.getScore());
					tri.setVideoUrl(cti.getVideo() != null ? cti.getVideo() : null);
					res.add(tri);
				}
			}
			if (addScore > 0) {
				if (s.getScore() == null) s.setScore(0);
				s.setScore(s.getScore().intValue() + addScore);
				studentMapper.updateStudentScoreById(s.getId(), addScore);
			}
			return BaseRsp.success(res);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return BaseRsp.fail(RspEnum.ERROR_PARAM);
		}
	}
	
	public static void main(String[] args) {
	}

}
