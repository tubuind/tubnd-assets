'use strict';

describe('Controller Tests', function() {

    describe('MtdDevicetype Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockMtdDevicetype, MockDeviceInfo;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockMtdDevicetype = jasmine.createSpy('MockMtdDevicetype');
            MockDeviceInfo = jasmine.createSpy('MockDeviceInfo');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'MtdDevicetype': MockMtdDevicetype,
                'DeviceInfo': MockDeviceInfo
            };
            createController = function() {
                $injector.get('$controller')("MtdDevicetypeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'astcoreApp:mtdDevicetypeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
