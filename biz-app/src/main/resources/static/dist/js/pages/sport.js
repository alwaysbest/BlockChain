/**
 * Created by Daniel on 2016/10/21.
 */
$(function () {
    var op1 = {
        title: {
            text: '运动类型统计',
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b}: {c}分钟({d}%)"
        },
        legend: {
            data: ['其他', '游泳', '篮球', '排球', '跑步'],
            y: 'bottom'
        },
        series: [
            {
                name: '运动类型',
                type: 'pie',
                radius: ['50%', '70%'],
                avoidLabelOverlap: false,
                label: {
                    normal: {
                        show: false,
                        position: 'center'
                    },
                    emphasis: {
                        show: true,
                        textStyle: {
                            fontSize: '30',
                            fontWeight: 'bold'
                        }
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                data: [
                    {value: 335, name: '其他'},
                    {value: 310, name: '游泳'},
                    {value: 234, name: '篮球'},
                    {value: 135, name: '排球'},
                    {value: 1548, name: '跑步'}
                ]
            }
        ]
    };

    var op2 = {
        title: {
            text: '运动时间段统计',
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c}分钟({d}%)"
        },
        legend: {
            y: 'bottom',
            data: ['00:00-8:00', '8:00-12:00', '12:00-16:00', '16:00-20:00', '20:00-24:00']
        },
        series: [
            {
                name: '运动时间',
                type: 'pie',
                radius: ['50%', '70%'],
                data: [
                    {value: 120, name: '00:00-8:00'},
                    {value: 250, name: '8:00-12:00'},
                    {value: 75, name: '12:00-16:00'},
                    {value: 678, name: '16:00-20:00'},
                    {value: 1548, name: '20:00-24:00'}
                ],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    var op3 = {
        title: {
            text: '历史运动情况',
            left:20
        },
        tooltip: {
            trigger: 'axis',
            formatter:"{b}<br/>{a0}:{c0}分钟<br/>{a1}:{c1}千米<br/>{a2}:{c2}大卡"
        },
        legend: {
            data: ['运动时间(min)', '运动量(km)', '能量消耗(大卡)']
        },
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            splitArea: {
                show: false
            },
            splitLine: {
                show: false
            },
            data: ['2016-10-10', '2016-10-11', '2016-10-12', '2016-10-13', '2016-10-14', '2016-10-15', '2016-10-16']
        },
        yAxis: {

            type: 'value'
        },
        series: [
            {
                name: '运动时间',
                type: 'line',
                stack: '总量',
                data: [40.4, 35.3, 0, 30.5, 90, 120.0, 65.0]
            },
            {
                name: '运动量',
                type: 'line',
                stack: '总量',
                data: [6.0, 5.5, 0, 4.0, 7.6, 14.5, 8.0]
            },
            {
                name: '消耗能量',
                type: 'line',
                stack: '总量',
                data: [380, 320, 0, 266, 423, 832, 410]
            }
        ]
    };

    var sportType = echarts.init(document.getElementById('sportTypeChart'));
    var sportPeriod = echarts.init(document.getElementById('sportPeriodChart'));
    var sportHistory = echarts.init(document.getElementById('sportHistoryChart'));
    sportType.setOption(op1);
    sportPeriod.setOption(op2);
    sportHistory.setOption(op3);


})