'use strict';

describe('Controller Tests', function() {

    describe('CifDevice Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCifDevice, MockCifMaster, MockDeviceInfo;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCifDevice = jasmine.createSpy('MockCifDevice');
            MockCifMaster = jasmine.createSpy('MockCifMaster');
            MockDeviceInfo = jasmine.createSpy('MockDeviceInfo');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'CifDevice': MockCifDevice,
                'CifMaster': MockCifMaster,
                'DeviceInfo': MockDeviceInfo
            };
            createController = function() {
                $injector.get('$controller')("CifDeviceDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'astcoreApp:cifDeviceUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
