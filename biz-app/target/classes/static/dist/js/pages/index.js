/**
 * Created by Daniel on 2016/10/17.
 */
$(function () {
    var option = {
        color: ['#3398DB'],
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        grid: {},
        xAxis: [
            {
                type: 'category',
                splitArea: {
                    show: false
                },
                splitLine: {
                    show: false
                },
                data: ['0min-20min', '20min-40min', '40min-60min', '60min-80min', '80min-100min', '100min-120min', '120min+'],
                axisTick: {
                    alignWithLabel: true
                }
            }
        ],
        yAxis: [
            {
                scale: true,
                splitArea: {
                    show: false
                },
                splitLine: {
                    show: false
                },
                type: 'value'
            }
        ],
        series: [
            {
                name: '人数',
                type: 'line',
                barWidth: '60%',
                areaStyle: {normal: {}},
                data: [1, 3, 10, 15, 17, 4, 3]
            }
        ]
    };


    var myChart = echarts.init(document.getElementById('sportTimeChart'));
    var myChart1 = echarts.init(document.getElementById('sportTimeChart1'));
    myChart.setOption(option);
    myChart1.setOption(option);
})