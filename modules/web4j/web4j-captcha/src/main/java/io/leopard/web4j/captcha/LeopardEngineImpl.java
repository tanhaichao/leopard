package io.leopard.web4j.captcha;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jhlabs.image.PinchFilter;
import com.jhlabs.math.ImageFunction2D;
import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.UniColorBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomListColorGenerator;
import com.octo.captcha.component.image.deformation.ImageDeformation;
import com.octo.captcha.component.image.deformation.ImageDeformationByBufferedImageOp;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.GlyphsPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.textpaster.glyphsvisitor.GlyphsVisitors;
import com.octo.captcha.component.image.textpaster.glyphsvisitor.OverlapGlyphsUsingShapeVisitor;
import com.octo.captcha.component.image.textpaster.glyphsvisitor.TranslateAllToRandomPointVisitor;
import com.octo.captcha.component.image.textpaster.glyphsvisitor.TranslateGlyphsVerticalRandomVisitor;
import com.octo.captcha.component.image.wordtoimage.DeformedComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.image.gimpy.GimpyFactory;

public class LeopardEngineImpl extends com.octo.captcha.engine.image.ImageCaptchaEngine implements LeopardEngine {

	protected static final Log logger = LogFactory.getLog(LeopardEngineImpl.class);

	// private static final String words =
	// "二十丁厂七卜人入八九几儿了力乃刀又三于干亏士工土才寸下大丈与万上小口巾山千乞川亿个勺久凡及夕丸么广亡门义之尸弓己已子卫也女飞刃习叉马乡丰王井开夫天无元专云扎艺木五支厅不太犬区历尤友匹车巨牙屯比互切瓦止少日中冈贝内水见午牛手毛气升长仁什片仆化仇币仍仅斤爪反介父从今凶分乏公仓月氏勿欠风丹匀乌凤勾文六方火为斗忆订计户认心尺引丑巴孔队办以允予劝双书幻玉刊示末未击打巧正扑扒功扔去甘世古节本术可丙左厉右石布龙平灭轧东卡北占业旧帅归且旦目叶甲申叮电号田由史只央兄叼叫另叨叹四生失禾丘付仗代仙们仪白仔他斥瓜乎丛令用甩印乐句匆册犯外处冬鸟务包饥主市立闪兰半汁汇头汉宁穴它讨写让礼训必议讯记永司尼民出辽奶奴加召皮边发孕圣对台矛纠母幼丝";
	// private String words;

	private static final String words = "abcdefghijklmnopqrstuvwsyz";

	private int width;
	private int height;

	@Override
	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Add a factory to the gimpy list
	 * 
	 * @return true if added false otherwise
	 */
	@SuppressWarnings("unchecked")
	public boolean addFactory(com.octo.captcha.image.ImageCaptchaFactory factory) {
		return factory != null && this.factories.add(factory);
	}

	@Override
	public void initialFactories() {
		WordGenerator dictionnaryWords = new RandomWordGenerator(words);
		// wordtoimage components
		TextPaster randomPaster = new GlyphsPaster(4, 4, new RandomListColorGenerator(

		new Color[] { //
				new Color(23, 170, 27),//
						new Color(220, 34, 11), //
						new Color(23, 67, 172) //
				}),

		new GlyphsVisitors[] {//
				new TranslateGlyphsVerticalRandomVisitor(1),//

						new OverlapGlyphsUsingShapeVisitor(3), //
						new TranslateAllToRandomPointVisitor() //
				});
		/*
		 * new TextVisitor[]{ new OverlapGlyphsTextVisitor(6) }, null
		 */

		// int width = this.getWidth();
		// int height = this.getHeight();
		BackgroundGenerator back = new UniColorBackgroundGenerator(width, height, Color.white);

		FontGenerator shearedFont = new RandomFontGenerator(50, 50, //
				new Font[] { new Font("nyala", Font.BOLD, 50), //
						new Font("Bell MT", Font.PLAIN, 50), //
						new Font("Credit valley", Font.BOLD, 50) //
				}, false);

		PinchFilter pinch = new PinchFilter();

		pinch.setAmount(-.5f);
		pinch.setRadius(30);
		pinch.setAngle((float) (Math.PI / 16));
		pinch.setCentreX(0.5f);
		pinch.setCentreY(-0.01f);
		pinch.setEdgeAction(ImageFunction2D.CLAMP);

		PinchFilter pinch2 = new PinchFilter();
		pinch2.setAmount(-.6f);
		pinch2.setRadius(70);
		pinch2.setAngle((float) (Math.PI / 16));
		pinch2.setCentreX(0.3f);
		pinch2.setCentreY(1.01f);
		pinch2.setEdgeAction(ImageFunction2D.CLAMP);

		PinchFilter pinch3 = new PinchFilter();
		pinch3.setAmount(-.6f);
		pinch3.setRadius(70);
		pinch3.setAngle((float) (Math.PI / 16));
		pinch3.setCentreX(0.8f);
		pinch3.setCentreY(-0.01f);
		pinch3.setEdgeAction(ImageFunction2D.CLAMP);

		List<ImageDeformation> textDef = new ArrayList<ImageDeformation>();
		textDef.add(new ImageDeformationByBufferedImageOp(pinch));
		textDef.add(new ImageDeformationByBufferedImageOp(pinch2));
		textDef.add(new ImageDeformationByBufferedImageOp(pinch3));

		// word2image 1
		WordToImage word2image = new DeformedComposedWordToImage(false, shearedFont, back, randomPaster, new ArrayList<ImageDeformation>(), new ArrayList<ImageDeformation>(), textDef

		);

		this.addFactory(new GimpyFactory(dictionnaryWords, word2image, false));
	}
}