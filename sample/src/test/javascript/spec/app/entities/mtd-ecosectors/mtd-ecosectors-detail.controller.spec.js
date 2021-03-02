'use strict';

describe('Controller Tests', function() {

    describe('MtdEcosectors Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockMtdEcosectors, MockCifMaster;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockMtdEcosectors = jasmine.createSpy('MockMtdEcosectors');
            MockCifMaster = jasmine.createSpy('MockCifMaster');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'MtdEcosectors': MockMtdEcosectors,
                'CifMaster': MockCifMaster
            };
            createController = function() {
                $injector.get('$controller')("MtdEcosectorsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'astcoreApp:mtdEcosectorsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
