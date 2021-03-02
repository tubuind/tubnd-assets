'use strict';

describe('Controller Tests', function() {

    describe('MtdDevicegroup Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockMtdDevicegroup, MockDeviceInfo, MockMtdUnit;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockMtdDevicegroup = jasmine.createSpy('MockMtdDevicegroup');
            MockDeviceInfo = jasmine.createSpy('MockDeviceInfo');
            MockMtdUnit = jasmine.createSpy('MockMtdUnit');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'MtdDevicegroup': MockMtdDevicegroup,
                'DeviceInfo': MockDeviceInfo,
                'MtdUnit': MockMtdUnit
            };
            createController = function() {
                $injector.get('$controller')("MtdDevicegroupDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'astcoreApp:mtdDevicegroupUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
