
package 桌面;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JPanel;
public class 罗盘时钟 extends JFrame {
	private int second= 0,minute=0,hour=0,yrar=2010,month=0,date=0,day=0;
	private String[] seconds={"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59"};
	private String[] minutes={"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59"};
	private String[] hours= {"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"};
	private String[] days= {"日","一","二","三","四","五","六"};
	private String[] dates;
	private String[] months= {"一","二","三","四","五","六","七","八","九","十","十一","十二"};
	private int[] yuefens= {31,28,31,30,31,30,31,31,30,31,30,31};
	private int x = 0,y = 0,d = 300;//位置以及半径
	private double pre = Math.PI*2/(seconds.length);//周长

	private GregorianCalendar calendar;
	private Font f=new Font("宋体", Font.BOLD, 50);
	private GlyphVector glyphVector=f.createGlyphVector(getFontMetrics(f).getFontRenderContext(),yrar+"" );
	private AffineTransform rot;
	public static void main(String[] args) {
		new 罗盘时钟();		
	}
	public 罗盘时钟() {
		setBounds(300, 50, 800, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);//去掉窗口样式
		setBackground(new Color(0,191,255,0));//颜色以及透明度		
		add(new MyPanel());
		setVisible(true);
		new Timer().schedule(new Task(), 1000, 1000);
	}
	class MyPanel extends JPanel {
		public MyPanel() {
			setBackground(new Color(255,255,255,0));//颜色以及透明度	
			if(yrar%4==0||yrar%400==0)//平年闰年判断
				yuefens[1]=29;
			dates=new String[yuefens[month]];
			for (int i = 1; i <=yuefens[month]; i++) {
				dates[i-1]=""+i;
			}

		}
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d= (Graphics2D) g;
			g2d.setFont(new Font("宋体", Font.BOLD, 20));   //字体
			g2d.translate(this.getWidth() / 2, this.getHeight() / 2);// 平移原点到图形环境的中心   	
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);//消除文字锯齿
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // 抗边缘锯齿

			Color tmpColor=g2d.getColor();
			Stroke sk=g2d.getStroke();
			g2d.setColor(Color.WHITE);  // 设置画笔颜色
			g2d.setStroke(new BasicStroke(25)); // 设置线条宽度	
			//背景
			g2d.drawOval(-307, -307, 615, 615);//圆环
			g2d.drawOval(-280, -280, 560, 560);
			g2d.drawOval(-250, -250, 500, 500);	 

			g2d.drawOval(-155, -155, 310, 310);	 
			g2d.drawOval(-125, -125, 250, 250);	 
			// g2d.fillRoundRect(80,100,100,100,60,40);//涂一个圆角矩形块
			g2d.setColor(tmpColor); //重置颜色
			g2d.setStroke(sk); // 重置线条宽度
			g2d.drawRoundRect(0, -320, 20, 210,10,10);//绘制圆角矩形，选中当前时间

			//	int xs=second;
			for(int i=0;i<seconds.length;i++){		
				rot = new AffineTransform();
				rot.rotate(pre,x,y);			
				g2d.drawString(seconds[second--],(float)(x),(float)(y-d));
				if (second<0) second=59;	
				g2d.transform(rot);						
			}

			//	int mxs=minute;
			for(int i=0;i<seconds.length;i++){
				rot = new AffineTransform();
				rot.rotate(pre,x,y);
				g2d.drawString(minutes[minute--],(float)(x),(float)(y-(d-30)));
				if (minute<0) minute=59;
				g2d.transform(rot);
			}	

			//	int hourxs=hour;
			for(int i=0;i<hours.length;i++){		
				rot = new AffineTransform();
				rot.rotate(Math.PI*2/hours.length,x,y);			
				g2d.drawString(hours[hour--],(float)(x),(float)(y-(d-60)));
				if (hour<0) hour=23;	
				g2d.transform(rot);						
			}

			//	int daysxs=day;
			for(int i=0;i<days.length;i++){		//星期
				rot = new AffineTransform();
				rot.rotate(Math.PI*2/days.length,x,y);	
				g2d.drawString("周",(float)(x),(float)(y-(d-90)));	
				g2d.drawString(days[day--],(float)(x),(float)(y-(d-120)));	
				if (day<0) day=6;	
				g2d.transform(rot);						
			}

			//	int datesxs=date;
			for(int i=0;i<yuefens[month];i++){		//天数
				rot = new AffineTransform();
				rot.rotate(Math.PI*2/yuefens[month],x,y);	
				if (date<1) date=yuefens[month];
				g2d.drawString(dates[--date],(float)(x),(float)(y-(d-150)));			
				g2d.transform(rot);						
			}

			//	int monthxs=month;
			for(int i=0;i<months.length;i++){		//月
				rot = new AffineTransform();
				rot.rotate(Math.PI*2/months.length,x,y);				
				g2d.drawString(months[month],(float)(x),(float)(y-(d-180)));	
				if (month<0) month=11;	
				g2d.transform(rot);						
			}

			//	g2d.drawString(yrar+"年", -20, 0);
			g2d.translate(-49,19);
			g2d.setColor(Color.WHITE);
			//	g2d.fill(shape);
			g2d.setColor(Color.BLUE.darker());
			g2d.setStroke(new BasicStroke(3));
			g2d.draw(glyphVector.getOutline());
		}		

	}
	class Task extends TimerTask {
		public void run() {
			calendar = new GregorianCalendar();
			calendar.setTime(new Date());
			yrar=calendar.get(Calendar.YEAR);					//获取年份
			month=calendar.get(Calendar.MONTH);					//获取月份 0-11
			date=calendar.get(Calendar.DATE);					//获取日 
			hour = calendar.get(Calendar.HOUR_OF_DAY);	//时（24小时制）	//时（12小时制）Calendar.HOUR
			minute = calendar.get(Calendar.MINUTE);		//分	
			second = calendar.get(Calendar.SECOND);		//秒				*/		
			day=calendar.get(Calendar.DAY_OF_WEEK)-1;				//周  {"日","一","二","三","四","五","六"};0-6

			/*		String[] strNow = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()).toString().split("-");//hh用12小时制 ，HH用24小时制
			yrar=Integer.parseInt(strNow[0]);			//获取年
			month=Integer.parseInt(strNow[1]);			//获取月
			date=Integer.parseInt(strNow[2]);			//获取日	 
			hour=Integer.parseInt(strNow[3]);			//获取时（24小时制）
			minute=Integer.parseInt(strNow[4]);			//获取分
			second=Integer.parseInt(strNow[5]);			//秒*/

			repaint();					
			System.out.println(yrar+"年"+month+"月"+date+"号"+day+"周"+hour+"-"+minute+"-"+second+"秒旋转="+6 * (Math.PI / 180) * second);
		}
	}
}































