package io.leopard.web4j.nobug.xss;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XssCheckerOnxxxImpl implements XssChecker {

	private static final Pattern pattern = Pattern.compile("[<'\"=]");

	private static final Pattern onxxxPattern = Pattern.compile("on[a-z]+", Pattern.CASE_INSENSITIVE);

	private static final Set<String> ONXXX_SET = new HashSet<String>();
	static {
		addOnxxx("onerror");
		addOnxxx("onclick");
		addOnxxx("onload");
		addOnxxx("onAbort");
		addOnxxx("onActivate");
		addOnxxx("onAfterPrint");
		addOnxxx("onAfterUpdate");
		addOnxxx("onBeforeActivate");
		addOnxxx("onBeforeCopy");
		addOnxxx("onBeforeCut");
		addOnxxx("onBeforeDeactivate");
		addOnxxx("onBeforeEditFocus");
		addOnxxx("onBeforePaste");
		addOnxxx("onBeforePrint");
		addOnxxx("onBeforeUnload");
		addOnxxx("onBegin");
		addOnxxx("onBlur");
		addOnxxx("onBounce");
		addOnxxx("onCellChange");
		addOnxxx("onChange");
		addOnxxx("onClick");
		addOnxxx("ontextMenu");
		addOnxxx("ontrolSelect");
		addOnxxx("onCopy");
		addOnxxx("onCut");
		addOnxxx("onDataAvailable");
		addOnxxx("onDataSetChanged");
		addOnxxx("onDataSetComplete");
		addOnxxx("onDblClick");
		addOnxxx("onDeactivate");
		addOnxxx("onDrag");
		addOnxxx("onDragEnd");
		addOnxxx("onDragLeave");
		addOnxxx("onDragEnter");
		addOnxxx("onDragOver");
		addOnxxx("onDragDrop");
		addOnxxx("onDrop");
		addOnxxx("onEnd");
		addOnxxx("onError");
		addOnxxx("onErrorUpdate");
		addOnxxx("onFilterChange");
		addOnxxx("onFinish");
		addOnxxx("onFocus");
		addOnxxx("onFocusIn");
		addOnxxx("onFocusOut");
		addOnxxx("onHelp");
		addOnxxx("onKeyDown");
		addOnxxx("onKeyPress");
		addOnxxx("onKeyUp");
		addOnxxx("onLayoutComplete");
		addOnxxx("onLoad");
		addOnxxx("onLoseCapture");
		addOnxxx("onMediaComplete");
		addOnxxx("onMediaError");
		addOnxxx("onMouseDown");
		addOnxxx("onMouseEnter");
		addOnxxx("onMouseLeave");
		addOnxxx("onMouseMove");
		addOnxxx("onMouseOut");
		addOnxxx("onMouseOver");
		addOnxxx("onMouseUp");
		addOnxxx("onMouseWheel");
		addOnxxx("onMove");
		addOnxxx("onMoveEnd");
		addOnxxx("onMoveStart");
		addOnxxx("onOutOfSync");
		addOnxxx("onPaste");
		addOnxxx("onPause");
		addOnxxx("onProgress");
		addOnxxx("onPropertyChange");
		addOnxxx("onReadyStateChange");
		addOnxxx("onRepeat");
		addOnxxx("onReset");
		addOnxxx("onResize");
		addOnxxx("onResizeEnd");
		addOnxxx("onResizeStart");
		addOnxxx("onResume");
		addOnxxx("onReverse");
		addOnxxx("onRowsEnter");
		addOnxxx("onRowExit");
		addOnxxx("onRowDelete");
		addOnxxx("onRowInserted");
		addOnxxx("onScroll");
		addOnxxx("onSeek");
		addOnxxx("onSelect");
		addOnxxx("onChange");
		addOnxxx("onSelectStart");
		addOnxxx("onStart");
		addOnxxx("onStop");
		addOnxxx("onSyncRestored");
		addOnxxx("onSubmit");
		addOnxxx("onTimeError");
		addOnxxx("onTrackChange");
		addOnxxx("onUnload");
		addOnxxx("onURLFlip");
	}

	protected static void addOnxxx(String name) {
		ONXXX_SET.add(name.toLowerCase());
	}

	@Override
	public boolean check(String value) {
		if (value.length() < 10) {
			return false;
		}
		// if (value.indexOf("=") == -1) {
		// return false;
		// }
		boolean hasBadCode = pattern.matcher(value).find();
		if (!hasBadCode) {
			return false;
		}
		Matcher m = onxxxPattern.matcher(value);
		while (m.find()) {
			String name = m.group();
			name = name.toLowerCase();
			if (ONXXX_SET.contains(name)) {
				return true;
			}
		}
		return false;
	}

}
