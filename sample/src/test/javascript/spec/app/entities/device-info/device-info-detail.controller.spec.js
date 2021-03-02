'use strict';

describe('Controller Tests', function() {

    describe('DeviceInfo Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockDeviceInfo, MockMtdDevicetype, MockMtdDevicegroup, MockDeviceTransaction, MockCifDevice;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockDeviceInfo = jasmine.createSpy('MockDeviceInfo');
            MockMtdDevicetype = jasmine.createSpy('MockMtdDevicetype');
            MockMtdDevicegroup = jasmine.createSpy('MockMtdDevicegroup');
            MockDeviceTransaction = jasmine.createSpy('MockDeviceTransaction');
            MockCifDevice = jasmine.createSpy('MockCifDevice');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'DeviceInfo': MockDeviceInfo,
                'MtdDevicetype': MockMtdDevicetype,
                'MtdDevicegroup': MockMtdDevicegroup,
                'DeviceTransaction': MockDeviceTransaction,
                'CifDevice': MockCifDevice
            };
            createController = function() {
                $injector.get('$controller')("DeviceInfoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'astcoreApp:deviceInfoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
