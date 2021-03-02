'use strict';

describe('Controller Tests', function() {

    describe('MtdCustomergroup Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockMtdCustomergroup, MockCifMaster;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockMtdCustomergroup = jasmine.createSpy('MockMtdCustomergroup');
            MockCifMaster = jasmine.createSpy('MockCifMaster');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'MtdCustomergroup': MockMtdCustomergroup,
                'CifMaster': MockCifMaster
            };
            createController = function() {
                $injector.get('$controller')("MtdCustomergroupDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'astcoreApp:mtdCustomergroupUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
