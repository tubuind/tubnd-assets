'use strict';

describe('Controller Tests', function() {

    describe('CifMaster Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCifMaster, MockMtdWard, MockMtdOrganization, MockMtdEcosectors, MockMtdCustomergroup, MockCifDevice;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCifMaster = jasmine.createSpy('MockCifMaster');
            MockMtdWard = jasmine.createSpy('MockMtdWard');
            MockMtdOrganization = jasmine.createSpy('MockMtdOrganization');
            MockMtdEcosectors = jasmine.createSpy('MockMtdEcosectors');
            MockMtdCustomergroup = jasmine.createSpy('MockMtdCustomergroup');
            MockCifDevice = jasmine.createSpy('MockCifDevice');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'CifMaster': MockCifMaster,
                'MtdWard': MockMtdWard,
                'MtdOrganization': MockMtdOrganization,
                'MtdEcosectors': MockMtdEcosectors,
                'MtdCustomergroup': MockMtdCustomergroup,
                'CifDevice': MockCifDevice
            };
            createController = function() {
                $injector.get('$controller')("CifMasterDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'astcoreApp:cifMasterUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
