# meinvwo一个关于RXjava，retrofit2的例子。
有些服务端的接口设计，会在返回的数据外层包裹一些额外信息，这些信息对于调试很有用，但本地显示是用不到的。使用 map() 可以把外层的格式剥掉，只留下本地会用到的核心格式。代码大致形式：api.getData()&#160;&#160;&#160;&#160;<b>
.map(response -> response.data)</b>&#160;&#160;&#160;&#160;
.subscribeOn(Schedulers.io())&#160;&#160;&#160;&#160;
.observeOn(AndroidSchedulers.mainThread())&#160;&#160;&#160;&#160;
.subscribe(observer);(详见本项目源码)当然，map() 也可以用于基于其他各种需求的格式转换。
