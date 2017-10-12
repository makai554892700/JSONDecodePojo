#view简化初始化工具包,同 viewinit 模块 https://github.com/makai554892700/JSONDecodePojo/tree/master/viewinit
* 使用方法
    * 与项目build.gradle内添加

            compile 'com.mayousheng.www:initview:0.0.1'
    * 注解于某view变量上方(用于修饰view于id的关系)，例：

            @ViewDesc(viewId = R.id.img)
            public ImageView img;
    * 将ViewUtils.initAllView(...)方法放置于正确位置(关键初始化方法)。
    例(Activity内)：

            @Override
            protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(getLayout());
                ViewUtils.initAllView(BaseActivity.class, this);
            }
      又例如(Holder内)：

            public BaseRecyclerHolder(Context context, View view) {
                super(view);
                ViewUtils.initAllView(BaseRecyclerHolder.class, this, view);
            }
      或者(fragment内)：

            @Override
            public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
                    , @Nullable Bundle savedInstanceState) {
                rootView = inflater.inflate(getLayoutId(), container, false);
                ViewUtils.initAllView(BaseFragment.class, this, rootView);
                return rootView;
            }
* 注意事项
    * 使用ViewDesc注解的变量必须为public修饰。
    * 使用ViewDesc注解的viewId属性必须赋值正确。
    * 必须于正确位置调用ViewUtils.initAllView(...)方法。
    * 具体可参考当前项目使用
