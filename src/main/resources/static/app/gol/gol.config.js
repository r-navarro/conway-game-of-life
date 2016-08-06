angular.module('gol.grid', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/gol', {
    templateUrl: 'app/gol/gol.html',
    controller: 'golController',
    controllerAs: 'vm'
  });
}]);