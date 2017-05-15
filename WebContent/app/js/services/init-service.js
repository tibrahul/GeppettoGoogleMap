(function() {
	var app = angular.module(Window.context.APP_NAME);
	app.service("InitService", ["$log", "$http", "$timeout", "$location",
			function($log, $http, $timeout, $location) {

				var self = this;
				
				$log.log("base Url with window ", $location.absUrl().split('/#')[0]);
				
				self.baseUrl=$location.absUrl().split('/#')[0];
				
				self.urls={};				
				
				self.init=function(){
					self.loadUrls(function(data){
                        $log.log("urls are loaded!");
					});
					$log.log("Init service has been initiated!");
				};		
				self.loadUrls= function(callback) {

					$log.log("Loading urls!..");

					$.ajax({
						url : "app/assets/context/serverUrl.json",
						dataType: "JSON",
						method: "GET",
						success : function(data) {
							$log.log("Success: ", data);
							if (callback) {
								callback(data);
							}							
							self.urls = data;
						},
						error : function(data) {
							$log.log("Error: ", data);
							if (callback) {
								callback(data);
							}
						}
					});
				};				
				self.getUrl=function(key){
					var url=self.baseUrl+self.urls[key];
					$log.log("getUrl: ",url);
					return url;
					
				};		
				self.init();
			} ]);
});