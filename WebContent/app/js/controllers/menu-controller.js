(function() {
	app.controller('MenuController', [ "$log","$rootScope","$scope", "$http", "$location", '$cookieStore', 'RestURL', '$timeout', "$routeParams", 'MenuInformation', '$modal', function($log, $rootScope , $scope, $http, $location, $cookieStore, RestURL, $timeout, $routeParams, MenuInfo, $modal) {

		//$rootScope.tabprojectactive = false ;
		
		var self = $scope;

		/** object to submit button value create/update */
		self.submit = 'Update';

		console.log("MenuInfo.projectId" + MenuInfo.projectId);
		/** TODO: need to get values from previous screen */
		self.project_id = MenuInfo.projectId;
		self.project_name = MenuInfo.projectName;

		/** holding single node object */
		self.node = {};

		/** holding menu object */
		self.menu = {
			label : '',
			description : '',
			name : '',
			auth_id : '',
			project_id : self.project_id,
			menu_details : []
		};

		/** holding all menu node objects */
		self.list = [];
		self.menu_list = [];

		/** initialize menu controller */
		self.init = function() {
			console.log('Init Manu');
			self.fetchActivityWithScreen();
		};

		$scope.openSaveDialog = function(size, msg) {
			var modalInstance = $modal.open({
				animation : true,
				size : size,
				templateUrl : 'app/views/en-US/templates/modals/project/savemessage.html',
				controller : 'ModalCtrl',
				resolve : {
					data : function() {
						return angular.copy(msg); // deep copy
					},
				}
			});
			modalInstance.result.then(function(dataFromModal) {
			}, function() {
				$rootScope.tabprojectactive = true ;
				$log.info('Modal dismissed at: ' + new Date());
			});
		};
		
		$scope.$on('activityChangeEvent', function(event, args) {
			self.fetchActivityWithScreen();
		});

		/** Fetch Activity and screen details */
		self.fetchActivityWithScreen = function() {
			console.log('menubuilder/find_screendetail/?project_id=' + self.project_id);
			$http.get(RestURL.baseURL + 'menubuilder/find_screendetail/?project_id=' + self.project_id).success(function(data) {
				// console.log('menu + ', JSON.stringify(data));
				self.menu_list = addCurrentNode(data);
			}).error(function() {
				$log.log('Unable to fetch activity details');
			});
		}

		/** on submit decide create or update */
		self.onSubmit = function() {
			if (angular.equals(self.submit, 'Create')) {
				self.createMenu();
			} else {
				self.updateMenu();
			}
		};

		/** create menu */
		self.createMenu = function() {
			self.menu = self.buildMenuMaster();
			$http.post(RestURL.baseURL + 'menubuilder/create_menubuilder/', self.menu).success(function(data) {
				$log.log('menu created..!');
			}).error(function() {
				$log.log('unable to create menu');
			});
		};

		/** update menu */
		self.updateMenu = function() {
			self.menu_list = removeElement(self.menu_list);
			// console.log('SERVer ' + angular.toJson(self.menu_list));
			$http.post(RestURL.baseURL + 'menubuilder/update_menudetail/', self.menu_list).success(function(data) {
				$log.log('menu updated..!');
				var msg = 'Menu successfully updated !';
				$scope.openSaveDialog('sm', msg);
				
			}).error(function() {
				$log.log('unable to update menu');
			});
		};

		/** initialize menu controller */
		self.init();

		/**
		 * remove currentNode element from list, it is used for to display
		 * dotted border on selected node not needed in server
		 */
		var addCurrentNode = function(list) {
			angular.forEach(list, function(value, key) {
				value['currentNode'] = false;
				angular.forEach(value.screen_detail, function(value, key) {
					value['currentNode'] = false;
				});
			});
			return list;
		};

		var removeElement = function(list) {
			angular.forEach(list, function(value, key) {
				delete value['currentNode'];
				if (value.hasOwnProperty('screen_detail')) {
					if (value.menu_tree.length != 0) {
						removeElement(value.screen_detail);
					}
				}
			});
			return list;
		}

		/** Build menu master // converting screen data into String */
		self.buildMenuMaster = function() {
			var temp = [];
			angular.copy(self.menu_list, temp);
			removeElement(temp);
			self.menu.menu_details = [];
			angular.forEach(temp, function(value, key) {
				var menu_tree = value.menu_tree;
				delete value['menu_tree'];
				value['menu_tree'] = JSON.stringify(menu_tree);
				self.menu.menu_details.push(value);
			});
			return self.menu;
		}

		/** Build Menu tree with activity and screens */
		self.parseActivtyList = function(data) {
			var menu_list = [];
			angular.forEach(data, function(activty, key) {
				/** Build screen tree */
				var menu_tree = self.parseScreens(activty.screen_detail);
				/** Build activity node */
				var activity_detail = {};
				activity_detail['id'] = activty.id;
				activity_detail['name'] = '';
				activity_detail['label'] = activty.label;
				activity_detail['activity_id'] = activty.id;
				activity_detail['menu_master_id'] = 0;
				activity_detail['description'] = '';
				activity_detail['currentNode'] = false;
				activity_detail['menu_tree'] = menu_tree;
				menu_list.push(activity_detail);
				/** End */
			});
			return menu_list;
		};

		/** Build screen tree */
		self.parseScreens = function(menu) {
			var menu_tree = [];
			angular.forEach(menu, function(scrn, key) {
				var screen_detail = {};
				screen_detail['id'] = scrn.id;
				screen_detail['screen_id'] = scrn.id;
				screen_detail['name'] = '';
				screen_detail['label'] = scrn.label;
				screen_detail['description'] = '';
				screen_detail['project_id'] = scrn.project_id, screen_detail['currentNode'] = false;
				screen_detail['menu_tree'] = [];
				menu_tree.push(screen_detail);
			});
			return menu_tree;
		};

		self.compareMenuMaster = function(activityDetails, existingMenuMaster) {
			/** ?? Find deleted Activities ** */
			var temp = [];
			var tempForScreens = [];
			var flag = false;
			var pos = 0;
			// Find deleted Objects
			for (var i = 0; i < existingMenuMaster.length; i++) {
				var existing = existingMenuMaster[i];
				for (j = 0; j < activityDetails.length; j++) {
					var actObj = activityDetails[j];
					if (existing.activity_id == actObj.id) {
						flag = true;
						// replace existing screen object with new //always
						existing['menu_tree'] = self.parseScreens(actObj.menu_tree);
						tempForScreens.push(existing);
					} else {
						flag = false;
					}
				}
				if (!flag) {
					temp.push(existing);
				}
			}

			for (var t = 0; t < self.menu_list.length; t++) {
				// remove deleted activities
				for (var x = 0; x < temp.length; x++) {
					if (self.menu_list[t].activity_id == temp[x].activity_id) {
						self.menu_list.splice(t, 1);
					}
				}
				// remove deleted and add new screens
				for (var x = 0; x < tempForScreens.length; x++) {
					if (self.menu_list[t].activity_id == tempForScreens[x].activity_id) {
						self.menu_list.splice(t, 1);// delete existing
						self.menu_list.splice(t, 0, tempForScreens[x]);// insert  new  one
					}
				}
			}

			/** ?? Find new Activities ** */
			var temp = [];
			var flag = false;
			// Find new Objects
			for (var i = 0; i < activityDetails.length; i++) {
				var actObj = activityDetails[i];
				for (j = 0; j < existingMenuMaster.length; j++) {
					var existing = existingMenuMaster[j];
					if (actObj.id == existing.activity_id) {
						flag = true;
					} else {
						flag = false;
					}
				}
				if (!flag) {
					temp.push(actObj);
				}
			}
			temp = self.parseActivtyList(temp);
			// add new activity objects
			for (var t = 0; t < temp.length; t++) {
				self.menu_list.push(temp[t]);
			}
		}
	} ]);
})();