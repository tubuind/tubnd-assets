'use strict';

describe('Controller Tests', function() {

    describe('MtdWard Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockMtdWard, MockMtdDistrict, MockCifMaster;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockMtdWard = jasmine.createSpy('MockMtdWard');
            MockMtdDistrict = jasmine.createSpy('MockMtdDistrict');
            MockCifMaster = jasmine.createSpy('MockCifMaster');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'MtdWard': MockMtdWard,
                'MtdDistrict': MockMtdDistrict,
                'CifMaster': MockCifMaster
            };
            createController = function() {
                $injector.get('$controller')("MtdWardDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'astcoreApp:mtdWardUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
