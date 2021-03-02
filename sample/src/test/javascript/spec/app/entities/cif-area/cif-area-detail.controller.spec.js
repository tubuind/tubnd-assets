'use strict';

describe('Controller Tests', function() {

    describe('CifArea Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCifArea, MockCifMaster, MockCifAreaDevice;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCifArea = jasmine.createSpy('MockCifArea');
            MockCifMaster = jasmine.createSpy('MockCifMaster');
            MockCifAreaDevice = jasmine.createSpy('MockCifAreaDevice');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'CifArea': MockCifArea,
                'CifMaster': MockCifMaster,
                'CifAreaDevice': MockCifAreaDevice
            };
            createController = function() {
                $injector.get('$controller')("CifAreaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'astcoreApp:cifAreaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
