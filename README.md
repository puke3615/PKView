# PKView
自定义View合集

RandomView:
根据设置的数据源，随机生成数据

api:
//设置数据源

setData(String... contents);
//随机取值

void change();
//设置字体大小

void setSize(int size);
//设置字体颜色

void setColor(int color);
//验证是否正确

boolean isTrue(String content);

note:

mPaint.getTextBounds(mText, 0, mText.length(), mBound);
测量text的边界值

canvas.drawText(mText, (getWidth() - mBound.width()) / 2 + getPaddingLeft(), (getHeight() + mBound.height()) / 2 + getPaddingTop(), mPaint);
居中显示


