(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('DeviceInfoDetailController', DeviceInfoDetailController);

    DeviceInfoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'DeviceInfo', 'MtdDevicetype', 'MtdDevicegroup', 'DeviceTransaction', 'CifDevice'];

    function DeviceInfoDetailController($scope, $rootScope, $stateParams, previousState, entity, DeviceInfo, MtdDevicetype, MtdDevicegroup, DeviceTransaction, CifDevice) {
        var vm = this;

        vm.deviceInfo = entity;
        vm.previousState = previousState.name;







        function onClick(params){
            console.log(params);
        };

        $scope.lineConfig = {
            theme:'default',
            event: [{click:onClick}],
            dataLoaded:true
        };
    
        $scope.lineOption = {
            title : {
                text: 'Biểu đồ nhiệt độ',
                subtext: 'hoàn toàn hư cấu'
            },
            tooltip : {
                trigger: 'axis'
            },
            legend: {
                data:['nhiệt độ cao nhất','nhiệt độ tối thiểu']
            },
            toolbox: {
                show : true,
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: false},
                    magicType : {show: true, type: ['line', 'bar']},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            calculable : true,
            xAxis : [
                {
                    type : 'category',
                    boundaryGap : false,
                    data : ['Thứ 2', 'Thứ 3', 'Thứ 4', 'Thứ 5', 'Thứ 6', 'Thứ 7', 'Chủ nhật']
                }
            ],
            yAxis : [
                {
                    type : 'value',
                    axisLabel : {
                        formatter: '{value} °C'
                    }
                }
            ],
            series : [
                {
                    name:'nhiệt độ cao nhất',
                    type:'line',
                    smooth: true,
                    data:[11, 11, 15, 13, 12, 13, 10],
                    markPoint : {
                        data : [
                            {type : 'max', name: 'tối đa'},
                            {type : 'min', name: 'tối thiểu'}
                        ]
                    },
                    markLine : {
                        data : [
                            {type : 'average', name: 'nghĩa là'}
                        ]
                    }
                },
                {
                    name:'nhiệt độ tối thiểu',
                    type:'line',
                    smooth: true,
                    data:[1, -2, 2, 5, 3, 2, 0],
                    markPoint : {
                        data : [
                            {name : 'tuần thấp', value : -2, xAxis: 1, yAxis: -1.5}
                        ]
                    },
                    markLine : {
                        data : [
                            {type : 'average', name : 'nghĩa là'}
                        ]
                    }
                }
            ]
        };



        $scope.gaugeConfig = {
            theme:'default',
            dataLoaded:true
        };
    
        $scope.gaugeOption = {
            tooltip : {
                formatter: "{a} <br/>{b} : {c}%"
            },
            toolbox: {
                feature: {
                    restore: {},
                    saveAsImage: {}
                }
            },
            series: [
                {
                    name: '业务指标',
                    type: 'gauge',
                    detail: {formatter:'{value} ℃'},
                    data: [{value: 25, name: '完成率'}],
                    min: 10,
                    max: 40
                }
            ]
        };



        $scope.stackAreaConfig = {
            theme:'default',
            dataLoaded:true
        };
    
        $scope.stackAreaOption = {
            title: {
                text: '堆叠区域图'
            },
            tooltip : {
                trigger: 'axis',
                axisPointer: {
                    type: 'cross',
                    label: {
                        backgroundColor: '#6a7985'
                    }
                }
            },
            legend: {
                data:['邮件营销','联盟广告','视频广告','直接访问','搜索引擎']
            },
            toolbox: {
                feature: {
                    saveAsImage: {}
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis : [
                {
                    boundaryGap : false,
                    data : ['周一','周二','周三','周四','周五','周六','周日']
                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series : [
                {
                    name:'联盟广告',
                    type:'line',
                    stack: '总量',
                    areaStyle: {normal: {}},
                    data:[0, 400, 600, 470, 290, 330, 0]
                },
                {
                    name:'视频广告',
                    type:'line',
                    stack: '总量',
                    areaStyle: {normal: {}},
                    data:[null, null, null, 154, 190, 330, 50]
                },
                {
                    name:'直接访问',
                    type:'line',
                    stack: '总量',
                    areaStyle: {normal: {}},
                    data:[null, null, null, null, 390, 330, 100]
                },
                {
                    name:'搜索引擎',
                    type:'line',
                    stack: '总量',
                    label: {
                        normal: {
                            show: true,
                            position: 'top'
                        }
                    },
                    areaStyle: {normal: {}},
                    data:[null, null, null, null, null, 1330, 60]
                }
            ]
        };



        $scope.verticalSlider = {
            value: 23,
            options: {
                floor: 0,
                ceil: 100,
                step: 10,
                vertical: true,
                showSelectionBar: true,
                showTicksValues: true,
                readOnly: true
            }
        };

        setTimeout(function() {
            $scope.$broadcast('rzSliderForceRender');
        });








        // ///////////////////////////////////////////

        // var datapoints = [];
        // for (var i = 1; i <= 31; i++) {
        //     datapoints.push({ x: i, y: Math.floor((Math.random() * 9) + 6) });
        // }

        // var pageload = {
        //     name: 'Nhiệt độ',
        //     datapoints: datapoints
        // };

        // $scope.data = [pageload];

        // $scope.config = {
        //     title: 'Nhiệt độ',
        //     //subtitle: 'Line Chart Subtitle',
        //     showXAxis: true,
        //     showYAxis: true,
        //     showLegend: true,
        //     stack: false,
        //     width: 900,
        //     height: 500
        // };

        // ///////////////////////////////////////////

        // var datapointsArea = [];
        // for (var i = 1; i <= 31; i++) {
        //     datapointsArea.push({ x: i, y: Math.floor((Math.random() * 9) + 6) });
        // }

        // var pageloadArea = {
        //     name: 'Chuyển động',
        //     datapoints: datapointsArea
        // };

        // $scope.dataArea = [pageloadArea];

        // $scope.configArea = {
        //     title: 'Chuyển động',
        //     //subtitle: 'Area Chart Subtitle',
        //     yAxis: { scale: true },
        //     debug: true,
        //     stack: true,
        //     width: 800,
        //     height: 500,
        // };

        // ///////////////////////////////////////////

        // var datapointsGauge = [
        //     { x: 'Nhiệt độ hiện tại', y: 23, min: 10, max: 40 }
        // ];

        // var pageloadGauge = {
        //     name: 'Chuyển động',
        //     datapoints: datapointsGauge
        // };

        // $scope.dataGauge = [pageloadGauge];

        // $scope.configGauge = {
        //     min: 10,
        //     max: 40,
        //     width: 500,
        //     height: 500,
        //     detail: {formatter:'{value} C'},
        // };

        // ///////////////////////////////////////////

        // $scope.slider_all_options = {
        //     minValue: 2,
        //     options: {
        //         floor: 0,
        //         ceil: 10,
        //         step: 1,
        //         precision: 0,
        //         draggableRange: false,
        //         showSelectionBar: false,
        //         hideLimitLabels: false,
        //         readOnly: false,
        //         disabled: false,
        //         showTicks: true,
        //         showTicksValues: false,
        //         vertical: true
        //     }
        // };

        // $scope.verticalSlider = {
        //     value: 23,
        //     options: {
        //         floor: 0,
        //         ceil: 100,
        //         step: 10,
        //         vertical: true,
        //         showSelectionBar: true,
        //         showTicksValues: true,
        //         readOnly: true
        //     }
        // };

        // $scope.chartObj = {
        //     min: 10,
        //     max: 40
        // };


        var unsubscribe = $rootScope.$on('astcoreApp:deviceInfoUpdate', function(event, result) {
            vm.deviceInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
