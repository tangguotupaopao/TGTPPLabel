# TGTPPLabel
Auto adding characters to labels

#Basic Function
Adding some characters to specific background image in the middle. You can specify the bounding box of the characters.
When there are two many characters, TGTPPLabel can adjust the size of the characters automatically. 

#Way to use
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        CharAutoLayout cal = new CharAutoLayout(this,614,210);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setTextSize(40);
        cal.setBackgroundResId(R.drawable.label);
        cal.setPaddingTop(0.35f);
        cal.setPaddingBottom(0.1f);
        cal.setPaddingLeft(0.06f);
        cal.setPaddingRight(0.06f);
        cal.draw(p, "山不在高");
        boolean res = cal.saveBitmap(Environment.getExternalStorageDirectory().getAbsolutePath()+"/test.png");
        Log.i("TAG", "path " + Environment.getExternalStorageDirectory().getAbsolutePath()+"/test.png"+" "+res);
        ImageView i = (ImageView)findViewById(R.id.imageView);
        i.setImageBitmap(cal.getBitmap());
    }
}
