package ses.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Title: IdentityCode
 * @Description: 验证码类
 * @author: Wang Zhaohua
 * @date: 2016-5-5上午9:21:50
 */
public class IdentityCode {
	/** 图片的宽度 */
	private int width = 160;
	/** 图片高度 */
	private int height = 40;
	/** 验证码个数 */
	private int codeCount = 4;
	/** 验证码干扰线数 */
	private int lineCount = 10;
	/** 验证码 */
	private String code = null;
	/** 验证码图片Buffer */
	private BufferedImage buffImg = null;
	Random random = new Random();

	public IdentityCode() {
		creatImage();
	}

	public IdentityCode(int width, int height) {
		this.width = width;
		this.height = height;
		creatImage();
	}

	public IdentityCode(int width, int height, int codeCount) {
		this.width = width;
		this.height = height;
		this.codeCount = codeCount;
		creatImage();
	}

	public IdentityCode(int width, int height, int codeCount, int lineCount) {
		this.width = width;
		this.height = height;
		this.codeCount = codeCount;
		this.lineCount = lineCount;
		creatImage();
	}

	/**
	 * @Title: creatImage
	 * @author: Wang Zhaohua
	 * @date: 2016-5-5 上午9:18:31
	 * @Description: 生成图片
	 * @param:
	 * @return: void
	 */
	private void creatImage() {
		/** 字体宽度 */
		int fontWidth = width / codeCount;
		/** 字体高度 */
		int fontHeight = height - 5;
		int codeY = height - 8;

		/** 图像 Buffer */
		buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = buffImg.getGraphics();
		// Graphics2D g = buffImg.createGraphics();
		/** 设置背景颜色 */
		g.setColor(getRandColor(230, 250));
		g.fillRect(0, 0, width, height);

		/** 设置字体 */
		// Font font1 = getFont(fontHeight);
		Font font = new Font("Fixedsys", Font.BOLD, fontHeight);
		g.setFont(font);

		/** 设置干扰线 */
		for (int i = 0; i < lineCount; i++) {
			int xs = random.nextInt(width);
			int ys = random.nextInt(height);
			int xe = xs + random.nextInt(width);
			int ye = ys + random.nextInt(height);
			g.setColor(getRandColor(1, 255));
			g.drawLine(xs, ys, xe, ye);
		}

		/** 添加噪点 */
		float yawpRate = 0.01f;// 噪声率
		int area = (int) (yawpRate * width * height);
		for (int i = 0; i < area; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);

			buffImg.setRGB(x, y, random.nextInt(255));
		}

		/** 得到随机字符 */
		String str1 = randomStr(codeCount);
		this.code = str1;
		for (int i = 0; i < codeCount; i++) {
			String strRand = str1.substring(i, i + 1);
			g.setColor(getRandColor(1, 255));
			// g.drawString(a,x,y);
			// a为要画出来的东西，x和y表示要画的东西最左侧字符的基线位于此图形上下文坐标系的 (x, y) 位置处
			g.drawString(strRand, i * fontWidth + 3, codeY);
		}

	}

	/**
	 * @Title: randomStr
	 * @author: Wang Zhaohua
	 * @date: 2016-5-5 上午9:18:13
	 * @Description: 得到随机字符
	 * @param: @param n
	 * @param: @return
	 * @return: String
	 */
	private String randomStr(int n) {
		// String str1 = "ABCDEFGHJKMNOPQRSTUVWXYZabcdefghjkmnopqrstuvwxyz1234567890";
		String str1 = "1234567890";
		String str2 = "";
		int len = str1.length() - 1;
		double r;
		for (int i = 0; i < n; i++) {
			r = (Math.random()) * len;
			str2 = str2 + str1.charAt((int) r);
		}
		return str2;
	}

	/**
	 * @Title: getRandColor
	 * @author: Wang Zhaohua
	 * @date: 2016-5-5 上午9:18:00
	 * @Description: 得到随机颜色
	 * @param: @param fc
	 * @param: @param bc
	 * @param: @return
	 * @return: Color
	 */
	private Color getRandColor(int fc, int bc) {// 给定范围获得随机颜色
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	/**
	 * @Title: getFont
	 * @author: Wang Zhaohua
	 * @date: 2016-5-5 上午9:20:56
	 * @Description: 产生随机字体
	 * @param: @param size
	 * @param: @return
	 * @return: Font
	 */
	public Font getFont(int size) {
		Random random = new Random();
		Font font[] = new Font[5];
		font[0] = new Font("Ravie", Font.PLAIN, size);
		font[1] = new Font("Antique Olive Compact", Font.PLAIN, size);
		font[2] = new Font("Fixedsys", Font.PLAIN, size);
		font[3] = new Font("Wide Latin", Font.PLAIN, size);
		font[4] = new Font("Gill Sans Ultra Bold", Font.PLAIN, size);
		return font[random.nextInt(5)];
	}

	/**
	 * @Title: shear
	 * @author: Wang Zhaohua
	 * @date: 2016-5-5 上午9:21:09
	 * @Description: 扭曲方法
	 * @param: @param g
	 * @param: @param w1
	 * @param: @param h1
	 * @param: @param color
	 * @return: void
	 */
	public void shear(Graphics g, int w1, int h1, Color color) {
		shearX(g, w1, h1, color);
		shearY(g, w1, h1, color);
	}

	private void shearX(Graphics g, int w1, int h1, Color color) {
		int period = random.nextInt(2);

		boolean borderGap = true;
		int frames = 1;
		int phase = random.nextInt(2);

		for (int i = 0; i < h1; i++) {
			double d = (double) (period >> 1) * Math.sin((double) i / (double) period + (6.2831853071795862D * (double) phase) / (double) frames);
			g.copyArea(0, i, w1, 1, (int) d, 0);
			if (borderGap) {
				g.setColor(color);
				g.drawLine((int) d, i, 0, i);
				g.drawLine((int) d + w1, i, w1, i);
			}
		}

	}

	private void shearY(Graphics g, int w1, int h1, Color color) {
		int period = random.nextInt(40) + 10; // 50;

		boolean borderGap = true;
		int frames = 20;
		int phase = 7;
		for (int i = 0; i < w1; i++) {
			double d = (double) (period >> 1) * Math.sin((double) i / (double) period + (6.2831853071795862D * (double) phase) / (double) frames);
			g.copyArea(i, 0, 1, h1, 0, (int) d);
			if (borderGap) {
				g.setColor(color);
				g.drawLine(i, (int) d, i, 0);
				g.drawLine(i, (int) d + h1, i, h1);
			}

		}

	}

	/**
	 * @Title: write
	 * @author: Wang Zhaohua
	 * @date: 2016-5-5 上午9:21:19
	 * @Description: 保存图片
	 * @param: @param sos
	 * @param: @throws IOException
	 * @return: void
	 */
	public void write(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.getSession().setAttribute("img-identity-code", this.getCode());
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		ServletOutputStream outputStream = response.getOutputStream();
		ImageIO.write(buffImg, "png", outputStream);
		outputStream.flush();
		outputStream.close();
	}

	public BufferedImage getBuffImg() {
		return buffImg;
	}

	public String getCode() {
		return code.toLowerCase();
	}

}