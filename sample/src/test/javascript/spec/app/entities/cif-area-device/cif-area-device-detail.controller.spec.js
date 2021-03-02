'use strict';

describe('Controller Tests', function() {

    describe('CifAreaDevice Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCifAreaDevice, MockCifArea, MockCifDevice;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCifAreaDevice = jasmine.createSpy('MockCifAreaDevice');
            MockCifArea = jasmine.createSpy('MockCifArea');
            MockCifDevice = jasmine.createSpy('MockCifDevice');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'CifAreaDevice': MockCifAreaDevice,
                'CifArea': MockCifArea,
                'CifDevice': MockCifDevice
            };
            createController = function() {
                $injector.get('$controller')("CifAreaDeviceDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'astcoreApp:cifAreaDeviceUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
