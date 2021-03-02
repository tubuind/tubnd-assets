'use strict';

describe('Controller Tests', function() {

    describe('MtdDistrict Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockMtdDistrict, MockMtdProvince, MockMtdWard;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockMtdDistrict = jasmine.createSpy('MockMtdDistrict');
            MockMtdProvince = jasmine.createSpy('MockMtdProvince');
            MockMtdWard = jasmine.createSpy('MockMtdWard');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'MtdDistrict': MockMtdDistrict,
                'MtdProvince': MockMtdProvince,
                'MtdWard': MockMtdWard
            };
            createController = function() {
                $injector.get('$controller')("MtdDistrictDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'astcoreApp:mtdDistrictUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
