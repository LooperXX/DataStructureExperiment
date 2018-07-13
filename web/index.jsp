<%@ page import="graph.menu" %><%--
  Created by IntelliJ IDEA.
  User: LooperXX
  Date: 2018/7/8
  Time: 15:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
    menu.initSystem();
    session.setAttribute("firstStart",1);
    if(session.getAttribute("message") == null){
        session.setAttribute("message","Welcome~");
    }

%>
<html>
<head>
    <meta charset="UTF-8">
    <!-- Bootstrap Core CSS -->
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Morris Charts CSS -->
    <link href="vendor/morrisjs/morris.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <script src="js/echarts.js"></script>
    <title>景区管理系统</title>
</head>
<body>
<div id="wrapper">
    <!-- Navigation -->
    <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
        <div class="navbar-header">
            <a class="navbar-brand" href="index.jsp">景区管理系统</a>
        </div>
        <!-- /.navbar-header -->

        <ul class="nav navbar-top-links navbar-right">
            <li class="dropdown" <% if(session.getAttribute("UID")!=null)//session has expired
                out.print("style='" + "display:none;" +"'"); %>>
                <a class="dropdown-toggle active" data-toggle="modal" data-target="#myModal" aria-expanded="false">
                    <i class="fa fa-user fa-lg"></i>
                </a>
                <!-- /.dropdown-user -->
            </li>
        </ul>
        <!-- /.navbar-top-links -->

        <div class="navbar-default sidebar" role="navigation">
            <div class="sidebar-nav navbar-collapse">
                <ul class="nav" id="side-menu">
                    <li class="sidebar-search">
                        <div class="input-group custom-search-form">
                            <input id="searchScene" type="text" class="form-control" placeholder="Search...">
                            <span class="input-group-btn">
                                <button id="searchSceneButton" onclick="searchScene();" class="btn btn-default" type="button" data-toggle="modal" data-target="#scene">
                                   GO!
                                </button>
                            </span>
                        </div>
                        <!-- /input-group -->
                    </li>

                    <li>
                        <a href="#" onclick="sceneGraph();"><i class="fa fa-map-marker fa-fw"></i> 景点分布图</a>
                    </li>
                    <li class="active">
                        <a href="#"><i class="fa fa-bar-chart-o fa-fw"></i> 景点排名<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a href="#" onclick="sortBy('受欢迎度','岔路数');">景点受欢迎度排序</a>
                            </li>
                            <li>
                                <a href="#" onclick="sortBy('岔路数','受欢迎度');">景点岔路数排序</a>
                            </li>
                        </ul>
                        <!-- /.nav-second-level -->
                    </li>
                    <li class="active">
                        <a href="#"><i class="fa fa-road fa-fw"></i> 景区导游线路<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a href="#" onclick="sceneSPath()">景区两点线路</a>
                            </li>
                            <li>
                                <a href="#" onclick="sPath();">全景区回路</a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-edit fa-fw"></i> 停车场车辆信息记录</a>
                    </li>
                </ul>
            </div>
            <!-- /.sidebar-collapse -->
        </div>
        <!-- /.navbar-static-side -->
    </nav>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h3 class="page-header"> 暮春者，春服既成，冠者五六人，童子六七人，浴乎沂，风乎舞雩，咏而归。</h3>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
        <div class="row">
            <div class="col-lg-8">
                <div id="showSceneGraph" class="panel panel-default" style="display: none">
                    <div id="graphHead" class="panel-heading">
                    </div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <div id="showSceneGraphMain" style="width: 700px;height: 700px"></div>
                    </div>
                    <!-- /.panel-body -->
                </div>
                <div id="sortChart" class="panel panel-default"  style="display: none">
                    <div id="sortChartHead" class="panel-heading">

                    </div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <div id="sortChartMain" style="width: 700px;height: 700px"></div>
                    </div>
                    <!-- /.panel-body -->
                </div>
                <!-- /.panel -->

                <%--<div class="panel panel-default">--%>
                    <%--<div class="panel-heading">--%>
                        <%--<i class="fa fa-clock-o fa-fw"></i> Responsive Timeline--%>
                    <%--</div>--%>
                    <%--<!-- /.panel-heading -->--%>
                    <%--<div class="panel-body">--%>
                    <%--</div>--%>
                    <%--<!-- /.panel-body -->--%>
                <%--</div>--%>
                <!-- /.panel -->
            </div>
            <!-- /.col-lg-8 -->
            <div class="col-lg-4">
                <div id="choose" class="panel panel-default" style="display: none">
                    <div id="chooseHead" class="panel-heading" >

                    </div>
                    <!-- /.panel-heading -->
                    <div id="chooseBody" class="panel-body">
                        <div class="form-group">
                            <label class="col-sm-3 control-label" style="margin: 5 0 0 0">起点: </label>
                            <div class="col-sm-7">
                                <select id="SPathStart" class="form-control">

                                </select>
                            </div>
                        </div>
                        <br><br>
                        <div id="chooseEnd" class="form-group">
                            <label class="col-sm-3 control-label"  style="margin: 5 0 0 0">终点: </label>
                            <div class="col-sm-7">
                                <select id="SPathEnd" class="form-control">

                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer" style="padding: 10px;">
                        <button class="btn btn-default " onclick="submitSPath()" > 提交 </button>
                    </div>
                    <!-- /.panel-body -->
                </div>
                <!-- /.panel -->
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <i class="fa fa-send fa-fw"></i> 公告栏
                    </div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        ${sessionScope.get("message")}
                    </div>
                    <!-- /.panel-body -->
                </div>
                <!-- /.panel -->
            </div>
        </div>
        <!-- /.row -->
    </div>
    <!-- /#page-wrapper -->
</div>
<div id="myModal" class="modal fade">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                    <span class="sr-only">Close</span>
                </button>
                <h4 class="modal-title"></h4>
            </div>
            <div class="modal-body" style="padding: 0px">
                <form method="post" action="login.jsp">
                    <br><br>
                    <div style="text-align: center;position: relative;">
                        <img src="img/img_avatar.png" alt="Avatar" class="avatar" style="width: 40%;border-radius: 50%;">
                    </div>
                    <br><br><br>
                    <div style="padding-left: 20%; padding-right: 20%;">
                        <div class="form-group input-group ">
                            <span class="input-group-addon">邮箱</span>
                            <input type="email" name="email" required class="form-control" placeholder="...">
                        </div>
                        <div class="form-group input-group">
                            <span class="input-group-addon">密码</span>
                            <input type="password" name="psw" required class="form-control" placeholder="...">
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-secondary">登陆</button>
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
                    </div>
                </form>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<div id="scene" class="modal fade">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                    <span class="sr-only">Close</span>
                </button>
                <h4 class="modal-title">景点关键字查询</h4>
            </div>
            <div class="modal-body">
                <div class="form-group input-group ">
                    <span class="input-group-addon">景区名称</span>
                    <input id="sceneSearchName" readonly class="form-control" type="text" placeholder="载入中...">
                </div>
                <div class="form-group input-group ">
                    <span class="input-group-addon">景区受欢迎度</span>
                    <input id="sceneSearchWelcomeDegree" readonly class="form-control" type="text" placeholder="载入中...">
                </div>
                <div class="form-group input-group ">
                    <span class="input-group-addon">景区是否有休息区</span>
                    <input id="sceneSearchHasRestArea" readonly class="form-control" type="text" placeholder="载入中...">
                </div>
                <div class="form-group input-group ">
                    <span  class="input-group-addon">景区是否有公共厕所</span>
                    <input  id="sceneSearchHasToilet" readonly class="form-control" type="text" placeholder="载入中...">
                </div>
                <div class="panel-group" id="accordion">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h4 class="panel-title">
                                <a data-toggle="collapse" data-parent="#accordion" href="#collapseThree" class="collapsed" aria-expanded="false" style="font-size: 14px">景区简介</a>
                            </h4>
                        </div>
                        <div id="collapseThree" class="panel-collapse collapse" aria-expanded="false" style="height: 0px;">
                            <div id="sceneSearchDescription" class="panel-body">

                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<!-- /#wrapper -->

<!-- jQuery -->
<script src="vendor/jquery/jquery.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="vendor/bootstrap/js/bootstrap.min.js"></script>

<!-- Metis Menu Plugin JavaScript -->
<script src="vendor/metisMenu/metisMenu.min.js"></script>
<!-- Custom Theme JavaScript -->
<script src="dist/js/sb-admin-2.js"></script>
<script type="text/javascript">
    var points = [];
    var edges = [];
    var myChart = echarts.init(document.getElementById('showSceneGraphMain'));
    var option = {};
    var newItemStyle = {
        color:'#ff0029'
    };
    var newLineStyle = {
        color : '#6aff4d',
        width : 4
    };
    $("#searchScene").keydown(function() {
        if (event.keyCode == "13") {//keyCode=13是回车键
            $('#searchSceneButton').click();
        }
    });

    function searchScene(){
        sceneName = $("#searchScene").val();
        if(sceneName === ""){
            var res = "查询失败";
            $("#sceneSearchName").val(res);
            $("#sceneSearchDescription").html(res);
            $("#sceneSearchWelcomeDegree").val(res);
            $("#sceneSearchHasRestArea").val(res);
            $("#sceneSearchHasToilet").val(res);
            return;
        }

        $.ajax({
            type : "POST",
            url : '/SceneServlet',
            dataType : "text",
            data : {'sceneName':sceneName},
            async : false,
            success : function(data) {
                var json = JSON.parse(data.trim("'"));
                var res = json[sceneName].split(",");
                console.log(res);
                $("#sceneSearchName").val(res[0]);
                $("#sceneSearchDescription").html(res[1]);
                $("#sceneSearchWelcomeDegree").val(res[2]);
                $("#sceneSearchHasRestArea").val(res[3]);
                $("#sceneSearchHasToilet").val(res[4]);
            },
            error : function() {
                var res = "查询失败";
                $("#sceneSearchName").val(res);
                $("#sceneSearchDescription").html(res);
                $("#sceneSearchWelcomeDegree").val(res);
                $("#sceneSearchHasRestArea").val(res);
                $("#sceneSearchHasToilet").val(res);
            }
        });
        $(" #searchScene ").val("");
    }

    function sceneGraph() {
        $('#graphHead').html("<i class=\"fa fa-map-marker fa-fw\"></i> 景区景点分布图");
        $('#choose').hide();
        $.ajax({
            type : "POST",
            url : '/GraphServlet',
            dataType : "text",
            data : null,
            async : false,
            success : function(data) {
                var json = JSON.parse(data);
                points = JSON.parse(json.data);
                edges = JSON.parse(json.links);
                console.log('edges',edges);
                // var json = JSON.parse(data.trim("'"));// 纯字符串拼接可用:>
                // 基于准备好的dom，初始化echarts实例

                // 指定图表的配置项和数据
                option = {
                    // title: {
                    //     text: '景点分布图'
                    // },
                    tooltip: {},
                    animationDurationUpdate: 1500,
                    animationEasingUpdate: 'quinticInOut',
                    series : [
                        {
                            type: 'graph',
                            layout: 'force',
                            symbolSize: 50,
                            roam: true,
                            label: {
                                normal: {
                                    show: true
                                }
                            },
                            itemStyle:{
                                label: {
                                    show: true
                                },
                                normal:{
                                    color:'#63B8FF'
                                }
                            },
                            edgeSymbolSize: [4, 10],
                            edgeLabel: {
                                normal: {
                                    textStyle: {
                                        fontSize: 20
                                    }
                                }
                            },
                            data: points,
                            // links: [],
                            links: edges,
                            force: {
                                repulsion : 1000,//节点之间的斥力因子。支持数组表达斥力范围，值越大斥力越大。
                                gravity : 0.2,//节点受到的向中心的引力因子。该值越大节点越往中心点靠拢。
                                edgeLength :[80,150],//边的两个节点之间的距离，这个距离也会受 repulsion。[10, 50] 。值越小则长度越长
                                layoutAnimation : true
                            },
                            tooltip: {
                                formatter: function (x) {
                                    return x.data.description;//设置提示框的内容和格式 节点和边都显示name属性
                                }
                            }
                        }
                    ]
                };

                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
                $('#showSceneGraph').show();
            },
            error : function() {
                console.log('请求有误');
            }
        });
    }

    function sceneSPath(){
        $('#sortChart').hide();
        //if($("canvas").length === 0){
        if($("#showSceneGraphMain").children("canvas").length === 0){
            sceneGraph();
            console.log("直接调用景区景点分布图");
        }
        $('#showSceneGraph').show();
        $('#graphHead').html("<i class=\"fa fa-road fa-fw\"></i> 景区两点道路");
        $('#chooseHead').html("<i class=\"fa fa-pencil fa-fw\"></i> 选择路线的起点和终点");
        $('#choose').show();
        $('#chooseEnd').show();
        var start = document.getElementById("SPathStart");
        var end = document.getElementById("SPathEnd");
        start.options.length = 0;
        end.options.length = 0;
        for(var i = 0; i < points.length; i++){ //循环添加多个值
            start.options.add(new Option(points[i].name,points[i].name));
            end.options.add(new Option(points[i].name,points[i].name));
        }
        option.series[0].data = points;
        option.series[0].links = edges;
        myChart.setOption(option, true);
    }

    function submitSPath() {
        if($('#chooseEnd').css("display") === 'none'){
            submitsPath();
            return;
        }
        var checkStart = $("#SPathStart").val();
        var checkEnd = $("#SPathEnd").val();
        console.log(checkStart,checkEnd);
        $.ajax({
            type : "POST",
            url : '/GraphSPathServlet',
            dataType : "text",
            data : {'start':checkStart,'end':checkEnd},
            async : false,
            success : function(data) {
                var json = JSON.parse(data);
                var dis = JSON.parse(json.dis);
                var path = JSON.parse(json.path);
                var pointIndex = [];
                var edgesIndex = [];
                for(var i = 0; i < path.length; i++){
                    pointIndex[i] = points.findIndex((element) =>
                        (element.name === path[i])
                    );
                }
                for(var i = 0; i < path.length - 1 ; i++){
                    edgesIndex[i] = edges.findIndex((element) =>
                        (element.source === path[i] && element.target === path[i+1] ||
                            element.target === path[i] && element.source === path[i+1])
                );
                }
                if(edgesIndex[0] === -1){
                    alert("查询失败");
                    return;
                }
                // 还原数据用;
                var prePoints = JSON.parse(JSON.stringify(points));
                var preEdges = JSON.parse(JSON.stringify(edges));

                for(i = 0; i < pointIndex.length; i++){
                    points[pointIndex[i]].itemStyle = newItemStyle;
                }

                for(i = 0; i < edgesIndex.length; i++){
                    edges[edgesIndex[i]].lineStyle = newLineStyle;
                }
                option.series[0].data = points;
                option.series[0].links = edges;
                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option, true);
                // 还原地图数据为初始地图备用
                points = JSON.parse(JSON.stringify(prePoints));
                edges = JSON.parse(JSON.stringify(preEdges));
                console.log('option',option);
            },
            error : function() {
                console.log('请求有误');
            }
        });
    }

    function sortBy(sort1,sort2){
        $.ajax({
            type : "POST",
            url : '/SortServlet',
            dataType : "text",
            data : {'sortBy':sort1},
            async : false,
            success : function(data) {
                var mySortChart = echarts.init(document.getElementById('sortChartMain'));
                mySortChart.clear();
                var json = JSON.parse(data);
                var name = JSON.parse(json.name);
                var welcomeDegree = JSON.parse(json.welcomeDegree);
                var degree = JSON.parse(json.degree);
                console.log('name',name);
                console.log('welcomeDegree',welcomeDegree);
                console.log('degree',degree);
                $('#sortChartHead').html("<i class=\"fa fa-map-marker fa-fw\"></i> 景区景点" + sort1 + "优先排序图");
                var yaxis = [];
                var series = [];
                yaxis[0] = {};
                yaxis[1] = {};
                series[0] = {};
                series[1] = {};
                yaxis[0].type = 'value';
                yaxis[1].type = 'value';
                yaxis[0].name = sort1;
                yaxis[1].name = sort2;
                series[0].name = sort1;
                series[1].name = sort2;
                series[0].type = 'bar';
                series[1].type = 'line';
                series[1].yAxisIndex = 1;
                yaxis[0].axisLabel = {};
                yaxis[1].axisLabel = {};
                if(sort1 === '受欢迎度'){
                    yaxis[0].axisLabel.formatter = '{value} 分';
                    yaxis[1].axisLabel.formatter = '{value} 个';
                    yaxis[0].min = Math.ceil(Math.min.apply(null, welcomeDegree)/2);
                    yaxis[1].min = Math.ceil(Math.min.apply(null, degree)/2);
                    yaxis[0].max = Math.max.apply(null, welcomeDegree) + 2;
                    yaxis[1].max = Math.max.apply(null, degree) + 2;
                    series[0].data = welcomeDegree;
                    series[1].data = degree;
                }else{
                    yaxis[0].axisLabel.formatter = '{value} 个';
                    yaxis[1].axisLabel.formatter = '{value} 分';
                    yaxis[0].min = Math.ceil(Math.min.apply(null, degree)/2);
                    yaxis[1].min = Math.ceil(Math.min.apply(null, welcomeDegree)/2);
                    yaxis[0].max = Math.max.apply(null, degree);
                    yaxis[1].max = Math.max.apply(null, welcomeDegree);
                    series[0].data = degree;
                    series[1].data = welcomeDegree;
                }
                console.log(yaxis[0].min,yaxis[0].max,yaxis[1].min,yaxis[1].max);
                yaxis[0].interval = Math.ceil((yaxis[0].max - yaxis[0].min) / 5);
                yaxis[1].interval = Math.ceil((yaxis[1].max - yaxis[1].min) / 5);
                var option1 = {
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'cross',
                            crossStyle: {
                                color: '#999'
                            }
                        }
                    },
                    toolbox: {
                        feature: {
                            dataView: {show: true, readOnly: false},
                            magicType: {show: true, type: ['line', 'bar']},
                            restore: {show: true},
                            saveAsImage: {show: true}
                        }
                    },
                    legend: {
                        data:[sort1,sort2]
                    },
                    xAxis: [
                        {
                            type: 'category',
                            data: name,
                            axisPointer: {
                                type: 'shadow'
                            }
                        }
                    ],
                    yAxis: yaxis,
                    series: series
                };
                mySortChart.setOption(option1);
                $('#choose').hide();
                $('#showSceneGraph').hide();
                $('#sortChart').show();
            },
            error : function() {
                console.log('请求有误');
            }
        });
    }

    function sPath(){
        sceneGraph();
        $('#sortChart').hide();
        $('#graphHead').html("<i class=\"fa fa-road fa-fw\"></i> 景区回路");
        $('#chooseHead').html("<i class=\"fa fa-pencil fa-fw\"></i> 选择路线的起点");
        $('#choose').show();
        $('#chooseEnd').hide();
        var start = document.getElementById("SPathStart");
        start.options.length = 0;
        for(var i = 0; i < points.length; i++){ //循环添加多个值
            start.options.add(new Option(points[i].name,points[i].name));
        }
    }

    function submitsPath(){
        var checkStart = $("#SPathStart").val();
        console.log(checkStart);
        $.ajax({
            type : "POST",
            url : '/SPathServlet',
            dataType : "text",
            data : {'start':checkStart},
            async : false,
            success : function(data) {
                var json = JSON.parse(data);
                var dis = JSON.parse(json.dis);
                var path = JSON.parse(json.path);
                var pointIndex = [];
                var edgesIndex = [];
                for(var i = 0; i < path.length; i++){
                    pointIndex[i] = points.findIndex((element) =>
                        (element.name === path[i])
                );
                }
                for(var i = 0; i < path.length - 1 ; i++){
                    edgesIndex[i] = edges.findIndex((element) =>
                        (element.source === path[i] && element.target === path[i+1] ||
                            element.target === path[i] && element.source === path[i+1])
                );
                }
                if(edgesIndex[0] === -1){
                    alert("查询失败");
                    return;
                }
                // 还原数据用;
                var prePoints = JSON.parse(JSON.stringify(points));
                var preEdges = JSON.parse(JSON.stringify(edges));

                for(i = 0; i < pointIndex.length; i++){
                    points[pointIndex[i]].itemStyle = newItemStyle;
                }

                for(i = 0; i < edgesIndex.length; i++){
                    edges[edgesIndex[i]].lineStyle = newLineStyle;
                }
                option.series[0].symbolSize = (value, params) => {
                    //根据数据params中的data来判定数据大小
                    switch (params.dataIndex) {
                        case pointIndex[0]:
                            return 80;
                        default:
                            return 50;
                    }
                }
                option.series[0].data = points;
                option.series[0].links = edges;
                console.log(option);
                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option, true);
                points = JSON.parse(JSON.stringify(prePoints));
                edges = JSON.parse(JSON.stringify(preEdges));
                option.series[0].symbolSize = 50;
            },
            error : function() {
                console.log('请求有误');
            }
        });
    }
</script>
</body>
</html>