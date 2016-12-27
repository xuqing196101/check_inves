package common.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import iss.model.ps.Article;
import iss.model.ps.ArticleType;
import iss.service.ps.ArticleService;
import iss.service.ps.ArticleTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TimerServiceImpl {
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private ArticleTypeService articleTypeService;
	
	@Scheduled(cron = "0 0 2 * * ?")
	@Lazy(value=false)
	public void TestTask(){
		articleTypeService.updateShowNum();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date nowdate = new Date();
		String time = sdf.format(nowdate);
		Calendar date = Calendar.getInstance();
		date.setTime(nowdate);
		date.set(Calendar.DATE, date.get(Calendar.DATE)-1);
		Date qianDate = null;
		String qiantime = "";
		try {
			qianDate = sdf.parse(sdf.format(date.getTime()));
			qiantime = sdf.format(qianDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nowTime", time);
		map.put("qianDate", qiantime);
		String id = "3";
		map.put("id", id);
		BigDecimal jcw = articleService.selectByTimer(map);
		ArticleType articleTypejcw = new ArticleType();
		articleTypejcw.setId("3");
		articleTypejcw.setShowNum(jcw.toString());
		map.clear();
		map.put("nowTime", time);
		map.put("qianDate", qiantime);
		String id2 = "8";
		map.put("id", id2);
		BigDecimal jcg = articleService.selectByTimer(map);
		ArticleType articleTypejcg = new ArticleType();
		articleTypejcg.setId("8");
		articleTypejcg.setShowNum(jcg.toString());
		map.clear();
		map.put("nowTime", time);
		map.put("qianDate", qiantime);
		String id3 = "13";
		map.put("id", id3);
		BigDecimal jcf = articleService.selectByTimer(map);
		ArticleType articleTypejcf = new ArticleType();
		articleTypejcf.setId("13");
		articleTypejcf.setShowNum(jcf.toString());
		map.clear();
		map.put("nowTime", time);
		map.put("qianDate", qiantime);
		String id4 = "18";
		map.put("id", id4);
		BigDecimal jcj = articleService.selectByTimer(map);
		ArticleType articleTypejcj = new ArticleType();
		articleTypejcj.setId("18");
		articleTypejcj.setShowNum(jcj.toString());
		map.clear();
		map.put("nowTime", time);
		map.put("qianDate", qiantime);
		String id5 = "24";
		map.put("id", id5);
		BigDecimal bcw = articleService.selectByTimer(map);
		ArticleType articleTypebcw = new ArticleType();
		articleTypebcw.setId("24");
		articleTypebcw.setShowNum(bcw.toString());
		map.clear();
		map.put("nowTime", time);
		map.put("qianDate", qiantime);
		String id6 = "29";
		map.put("id", id6);
		BigDecimal bcg = articleService.selectByTimer(map);
		ArticleType articleTypebcg = new ArticleType();
		articleTypebcg.setId("29");
		articleTypebcg.setShowNum(bcg.toString());
		map.clear();
		map.put("nowTime", time);
		map.put("qianDate", qiantime);
		String id7 = "29";
		map.put("id", id7);
		BigDecimal bcf = articleService.selectByTimer(map);
		ArticleType articleTypebcf = new ArticleType();
		articleTypebcf.setId("34");
		articleTypebcf.setShowNum(bcf.toString());
		map.clear();
		map.put("nowTime", time);
		map.put("qianDate", qiantime);
		String id8 = "39";
		map.put("id", id8);
		BigDecimal bcj = articleService.selectByTimer(map);
		ArticleType articleTypebcj = new ArticleType();
		articleTypebcj.setId("39");
		articleTypebcj.setShowNum(bcj.toString());
		map.clear();
		map.put("nowTime", time);
		map.put("qianDate", qiantime);
		String id9 = "46";
		map.put("id", id9);
		BigDecimal jzw = articleService.selectByTimer(map);
		ArticleType articleTypejzw = new ArticleType();
		articleTypejzw.setId("46");
		articleTypejzw.setShowNum(jzw.toString());
		map.clear();
		map.put("nowTime", time);
		map.put("qianDate", qiantime);
		String id10 = "51";
		map.put("id", id10);
		BigDecimal jzg = articleService.selectByTimer(map);
		ArticleType articleTypejzg = new ArticleType();
		articleTypejzg.setId("51");
		articleTypejzg.setShowNum(jzg.toString());
		map.clear();
		map.put("nowTime", time);
		map.put("qianDate", qiantime);
		String id11 = "56";
		map.put("id", id11);
		BigDecimal jzf = articleService.selectByTimer(map);
		ArticleType articleTypejzf = new ArticleType();
		articleTypejzf.setId("56");
		articleTypejzf.setShowNum(jzf.toString());
		map.clear();
		map.put("nowTime", time);
		map.put("qianDate", qiantime);
		String id12 = "61";
		map.put("id", id12);
		BigDecimal jzj = articleService.selectByTimer(map);
		ArticleType articleTypejzj = new ArticleType();
		articleTypejzj.setId("61");
		articleTypejzj.setShowNum(jzj.toString());
		map.clear();
		map.put("nowTime", time);
		map.put("qianDate", qiantime);
		String id13 = "67";
		map.put("id", id13);
		BigDecimal bzw = articleService.selectByTimer(map);
		ArticleType articleTypebzw = new ArticleType();
		articleTypebzw.setId("67");
		articleTypebzw.setShowNum(bzw.toString());
		map.clear();
		map.put("nowTime", time);
		map.put("qianDate", qiantime);
		String id14 = "72";
		map.put("id", id14);
		BigDecimal bzg = articleService.selectByTimer(map);
		ArticleType articleTypebzg = new ArticleType();
		articleTypebzg.setId("72");
		articleTypebzg.setShowNum(bzg.toString());
		map.clear();
		map.put("nowTime", time);
		map.put("qianDate", qiantime);
		String id15 = "77";
		map.put("id", id15);
		BigDecimal bzf = articleService.selectByTimer(map);
		ArticleType articleTypebzf = new ArticleType();
		articleTypebzf.setId("77");
		articleTypebzf.setShowNum(bzf.toString());
		map.clear();
		map.put("nowTime", time);
		map.put("qianDate", qiantime);
		String id16 = "82";
		map.put("id", id16);
		BigDecimal bzj = articleService.selectByTimer(map);
		ArticleType articleTypebzj = new ArticleType();
		articleTypebzj.setId("82");
		articleTypebzj.setShowNum(bzj.toString());
		map.clear();
		map.put("nowTime", time);
		map.put("qianDate", qiantime);
		String id17 = "89";
		map.put("id", id17);
		BigDecimal jdw = articleService.selectByTimer(map);
		ArticleType articleTypejdw = new ArticleType();
		articleTypejdw.setId("89");
		articleTypejdw.setShowNum(jdw.toString());
		map.clear();
		map.put("nowTime", time);
		map.put("qianDate", qiantime);
		String id18 = "90";
		map.put("id", id18);
		BigDecimal jdg = articleService.selectByTimer(map);
		ArticleType articleTypejdg = new ArticleType();
		articleTypejdg.setId("90");
		articleTypejdg.setShowNum(jdg.toString());
		map.clear();
		map.put("nowTime", time);
		map.put("qianDate", qiantime);
		String id19 = "91";
		map.put("id", id19);
		BigDecimal jdf = articleService.selectByTimer(map);
		ArticleType articleTypejdf = new ArticleType();
		articleTypejdf.setId("91");
		articleTypejdf.setShowNum(jdf.toString());
		map.clear();
		map.put("nowTime", time);
		map.put("qianDate", qiantime);
		String id20 = "92";
		map.put("id", id20);
		BigDecimal jdj = articleService.selectByTimer(map);
		ArticleType articleTypejdj = new ArticleType();
		articleTypejdj.setId("92");
		articleTypejdj.setShowNum(jdj.toString());
		map.clear();
		map.put("nowTime", time);
		map.put("qianDate", qiantime);
		String id21 = "94";
		map.put("id", id21);
		BigDecimal bdw = articleService.selectByTimer(map);
		ArticleType articleTypebdw = new ArticleType();
		articleTypebdw.setId("94");
		articleTypebdw.setShowNum(bdw.toString());
		map.clear();
		map.put("nowTime", time);
		map.put("qianDate", qiantime);
		String id22 = "95";
		map.put("id", id22);
		BigDecimal bdg = articleService.selectByTimer(map);
		ArticleType articleTypebdg = new ArticleType();
		articleTypebdg.setId("95");
		articleTypebdg.setShowNum(bdg.toString());
		map.clear();
		map.put("nowTime", time);
		map.put("qianDate", qiantime);
		String id23 = "96";
		map.put("id", id23);
		BigDecimal bdf = articleService.selectByTimer(map);
		ArticleType articleTypebdf = new ArticleType();
		articleTypebdf.setId("96");
		articleTypebdf.setShowNum(bdf.toString());
		map.clear();
		map.put("nowTime", time);
		map.put("qianDate", qiantime);
		String id24 = "97";
		map.put("id", id24);
		BigDecimal bdj = articleService.selectByTimer(map);
		ArticleType articleTypebdj = new ArticleType();
		articleTypebdj.setId("97");
		articleTypebdj.setShowNum(bdj.toString());
		articleTypeService.updateByPrimaryKey(articleTypejcw);
		articleTypeService.updateByPrimaryKey(articleTypejcg);
		articleTypeService.updateByPrimaryKey(articleTypejcf);
		articleTypeService.updateByPrimaryKey(articleTypejcj);
		articleTypeService.updateByPrimaryKey(articleTypebcw);
		articleTypeService.updateByPrimaryKey(articleTypebcg);
		articleTypeService.updateByPrimaryKey(articleTypebcf);
		articleTypeService.updateByPrimaryKey(articleTypebcj);
		articleTypeService.updateByPrimaryKey(articleTypejzw);
		articleTypeService.updateByPrimaryKey(articleTypejzg);
		articleTypeService.updateByPrimaryKey(articleTypejzf);
		articleTypeService.updateByPrimaryKey(articleTypejzj);
		articleTypeService.updateByPrimaryKey(articleTypebzw);
		articleTypeService.updateByPrimaryKey(articleTypebzg);
		articleTypeService.updateByPrimaryKey(articleTypebzf);
		articleTypeService.updateByPrimaryKey(articleTypebzj);
		articleTypeService.updateByPrimaryKey(articleTypejdw);
		articleTypeService.updateByPrimaryKey(articleTypejdg);
		articleTypeService.updateByPrimaryKey(articleTypejdf);
		articleTypeService.updateByPrimaryKey(articleTypejdj);
		articleTypeService.updateByPrimaryKey(articleTypebdw);
		articleTypeService.updateByPrimaryKey(articleTypebdg);
		articleTypeService.updateByPrimaryKey(articleTypebdf);
		articleTypeService.updateByPrimaryKey(articleTypebdj);
	}
}
