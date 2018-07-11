<%--
  Created by IntelliJ IDEA.
  User: LooperXX
  Date: 2018/7/8
  Time: 17:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% if(session.getAttribute("UID")==null)//session has expired
    out.println("<script>window.location.href='"+"index.jsp"+"'</script>"); //redirect %>
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
    <title>景区管理系统--管理界面</title>
</head>
<body>
<div id="wrapper">
    <!-- Navigation -->
    <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
        <div class="navbar-header">
            <a href="index.jsp" class="navbar-brand">景区管理系统--管理界面</a>
        </div>
        <!-- /.navbar-header -->
        <div class="navbar-default sidebar" role="navigation">
            <div class="sidebar-nav navbar-collapse">
                <ul class="nav" id="side-menu">
                    <li class="active">
                        <a href="#"><i class="fa fa-cog fa-fw"></i> 景点管理<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a href="#" data-toggle="modal" data-target="#addSModal">景点添加</a>
                            </li>
                            <li>
                                <a href="#" data-toggle="modal" data-target="#deleteSModal">景点删除</a>
                            </li>
                            <li>
                                <a href="#" data-toggle="modal" data-target="#recoverSModal">景点恢复</a>
                            </li>
                        </ul>
                        <!-- /.nav-second-level -->
                    </li>
                    <li class="active">
                        <a href="#"><i class="fa fa-road fa-fw"></i> 道路管理<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a href="#" data-toggle="modal" data-target="#addEModal">道路添加</a>
                            </li>
                            <li>
                                <a href="#" data-toggle="modal" data-target="#deleteEModal">道路删除</a>
                            </li>
                            <li>
                                <a href="#" data-toggle="modal" data-target="#recoverEModal">道路恢复</a>
                            </li>
                        </ul>
                        <!-- /.nav-second-level -->
                    </li>
                    <li>
                        <a href="#" onclick="showTextArea();"><i class="fa fa-edit fa-fw"></i> 发布公告</a>
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
                <div id="Graph" class="panel panel-default">
                    <div id="graphHead" class="panel-heading">
                        <i class="fa fa-cog fa-fw"></i> 景区景点与道路管理可视化
                    </div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <div id="GraphMain" style="width: 700px;height: 700px"></div>
                    </div>
                    <!-- /.panel-body -->
                </div>
            </div>
            <div class="col-lg-4">
                <!-- /.panel -->
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <i class="fa fa-send fa-fw"></i> 公告栏
                    </div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <label id="newMessage">${sessionScope.get("message")}</label>
                        <textarea id="messageTextarea" class="form-control" style="display: none;"></textarea>
                    </div>
                    <!-- /.panel-body -->
                    <div id="sendMessageFooter" class="modal-footer" style="display: none;">
                        <button class="btn btn-default" onclick="sendMessage()"> 提交 </button>
                    </div>
                </div>
            </div>
        </div>
        <!-- /.row -->
    </div>
    <!-- /#page-wrapper -->
    <div id="addEModal" class="modal fade">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                        <span class="sr-only">Close</span>
                    </button>
                    <h4 class="modal-title">道路添加</h4>
                </div>
                <div class="modal-body">
                    <p class="control-label">请输入新路的起点与终点即路线长度：</p>
                    <p class="control-label">样例: 花卉园,北门,9</p>
                    <input id="addEinput" type="text" class="form-control" placeholder="请输入...">
                </div>
                <div class="modal-footer">
                    <button class="btn btn-default" onclick="addE()"> 提交 </button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

    <div id="deleteEModal" class="modal fade">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                        <span class="sr-only">Close</span>
                    </button>
                    <h4 class="modal-title">道路删除</h4>
                </div>
                <div class="modal-body">
                    <p class="control-label">请输入想要删除的景区道路：</p>
                    <p class="control-label">样例: 仙武湖,碧水潭</p>
                    <input id="deleteEinput" type="text" class="form-control" placeholder="请输入...">
                </div>
                <div class="modal-footer">
                    <button class="btn btn-default" onclick="deleteE()"> 提交 </button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

    <div id="recoverEModal" class="modal fade">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                        <span class="sr-only">Close</span>
                    </button>
                    <h4 class="modal-title">道路恢复</h4>
                </div>
                <div class="modal-body">
                    <p class="control-label">请输入预恢复的道路名称：</p>
                    <p class="control-label">样例: 仙武湖,碧水潭</p>
                    <input id="recoverEinput" type="text" class="form-control" placeholder="请输入...">
                </div>
                <div class="modal-footer">
                    <button class="btn btn-default" onclick="recoverE()"> 提交 </button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

    <div id="addSModal" class="modal fade">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                        <span class="sr-only">Close</span>
                    </button>
                    <h4 class="modal-title">景点添加</h4>
                </div>
                <div class="modal-body">
                    <p class="control-label">请依次输入新增景点名称,邻接景点个数以及邻接景点名称与距离: </p>
                    <p class="control-label">样例: 老虎滩,2;狮子山,22;北门,9</p>
                    <input id="addSinput" type="text" class="form-control" placeholder="请输入...">
                </div>
                <div class="modal-footer">
                    <button class="btn btn-default" onclick="addS()"> 提交 </button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

    <div id="deleteSModal" class="modal fade">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                        <span class="sr-only">Close</span>
                    </button>
                    <h4 class="modal-title">景点删除</h4>
                </div>
                <div class="modal-body">
                    <p class="control-label">请输入预删除的景点名称: </p>
                    <input id="deleteSinput" type="text" class="form-control" placeholder="请输入...">
                </div>
                <div class="modal-footer">
                    <button class="btn btn-default" onclick="deleteS()"> 提交 </button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

    <div id="recoverSModal" class="modal fade">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                        <span class="sr-only">Close</span>
                    </button>
                    <h4 class="modal-title">景点恢复</h4>
                </div>
                <div class="modal-body">
                    <p class="control-label">请输入预恢复的景点名称: </p>
                    <input id="recoverSinput" type="text" class="form-control" placeholder="请输入...">
                </div>
                <div class="modal-footer">
                    <button class="btn btn-default" onclick="recoverS()"> 提交 </button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

</div>
<!-- jQuery -->
<script src="vendor/jquery/jquery.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="vendor/bootstrap/js/bootstrap.min.js"></script>

<!-- Metis Menu Plugin JavaScript -->
<script src="vendor/metisMenu/metisMenu.min.js"></script>
<!-- Custom Theme JavaScript -->
<script src="dist/js/sb-admin-2.js"></script>
<script type="text/javascript">
    var myChart = echarts.init(document.getElementById('GraphMain'));
    window.onload = function(){
        getGraph();
    };
    
    function getGraph(){
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
                // 指定图表的配置项和数据
                var option = {
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
            },
            error : function() {
                console.log('请求有误');
            }
        });
    }

    function addE(){
        var input = $('#addEinput').val();
        $.ajax({
            type : "POST",
            url : '/ManageServlet',
            dataType : "text",
            data : {'type':'addE','input': input},
            async : false,
            success : function(data) {
                console.log(data);
                if(!(data === "ok")){
                    alert("您的输入有误:<");
                }else{
                    myChart.clear();
                    getGraph();
                }
            },
            error : function() {
                alert("服务器连接失败:<");
            }
        });
        $('#addEinput').val("");
        $('#addEModal').modal('hide')
    }

    function deleteE(){
        var input = $('#deleteEinput').val();
        $.ajax({
            type : "POST",
            url : '/ManageServlet',
            dataType : "text",
            data : {'type':'deleteE','input': input},
            async : false,
            success : function(data) {
                console.log(data);
                if(!(data === "ok")){
                    alert("您的输入有误:<");
                }else{
                    myChart.clear();
                    getGraph();
                }
            },
            error : function() {
                alert("服务器连接失败:<");
            }
        });
        input = $('#deleteEinput').val("");
        $('#deleteEModal').modal('hide')
    }

    function recoverE(){
        var input = $('#recoverEinput').val();
        $.ajax({
            type : "POST",
            url : '/ManageServlet',
            dataType : "text",
            data : {'type':'recoverE','input': input},
            async : false,
            success : function(data) {
                console.log(data);
                if(!(data === "ok")){
                    alert("您的输入有误:<");
                }else{
                    myChart.clear();
                    getGraph();
                }
            },
            error : function() {
                alert("服务器连接失败:<");
            }
        });
        input = $('#recoverEinput').val("");
        $('#recoverEModal').modal('hide')
    }

    function addS(){
        var input = $('#addSinput').val();
        $.ajax({
            type : "POST",
            url : '/ManageServlet',
            dataType : "text",
            data : {'type':'addS','input': input},
            async : false,
            success : function(data) {
                console.log(data);
                if(!(data === "ok")){
                    alert("您的输入有误:<");
                }else{
                    myChart.clear();
                    getGraph();
                }
            },
            error : function() {
                alert("服务器连接失败:<");
            }
        });
        $('#addSinput').val("");
        $('#addSModal').modal('hide')
    }

    function deleteS(){
        var input = $('#deleteSinput').val();
        $.ajax({
            type : "POST",
            url : '/ManageServlet',
            dataType : "text",
            data : {'type':'deleteS','input': input},
            async : false,
            success : function(data) {
                console.log(data);
                if(!(data === "ok")){
                    alert("您的输入有误:<");
                }else{
                    myChart.clear();
                    getGraph();
                }
            },
            error : function() {
                alert("服务器连接失败:<");
            }
        });
        $('#deleteSinput').val("");
        $('#deleteSModal').modal('hide')
    }

    function recoverS(){
        var input = $('#recoverSinput').val();
        $.ajax({
            type : "POST",
            url : '/ManageServlet',
            dataType : "text",
            data : {'type':'recoverS','input': input},
            async : false,
            success : function(data) {
                console.log(data);
                if(!(data === "ok")){
                    alert("您的输入有误:<");
                }else{
                    myChart.clear();
                    getGraph();
                }
            },
            error : function() {
                alert("服务器连接失败:<");
            }
        });
        input = $('#recoverSinput').val("");
        $('#recoverSModal').modal('hide')
    }

    function sendMessage(){
        var input = $('#messageTextarea').val();
        $('#newMessage').html(input);
        $.ajax({
            type : "POST",
            url : '/ManageServlet',
            dataType : "text",
            data : {'type':'message','input': input},
            async : false,
            success : function(data) {
                console.log(data);
                if(!(data === "ok")){
                    alert("您的输入有误:<");
                }else{

                }
            },
            error : function() {
                alert("服务器连接失败:<");
            }
        });
        $('#messageTextarea').val("");
        $('#messageTextarea').hide();
    }
    function showTextArea() {
        $('#messageTextarea').show();
        $('#sendMessageFooter').show();
        $('#newMessage').html("请输入新公告内容: ");
    }
</script>
</body>
</html>
