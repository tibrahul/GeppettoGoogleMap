var id = 0;
var index = 0;
var orientation = 'portrait';
var not_allowed = {
  third_level: {
    drop: null,
    drag: null,
    warned: false
  }
};
var nodeTree = {
  screen: {
    id: 0,
    name: '',
    description: '',
    role:'',
    label: '',
    notes: '',
    type: 'screen',
    width: 0,
    height: 0,
    client_device_type_id: 0,
    client_device_type: '',
    client_device_type_name: '',
    client_device_type_label: '',
    client_device_type_description: '',
    client_device_screen_size: '',
    client_device_resolution: '',
    client_device_ppcm: '',
    client_device_type_os_name: '',
    client_device_type_os_version: '',
    landscape_image_name: '',
    portrait_image_name: '',
    orientation: 'portrait',
    orientation_locked: '',
    client_language_type: '',
    client_library_type: '',
    activity_id: 0,
    projectid: 0,
    primary_noun_id: 0,
    secondary_noun_ids: [],
    human_language_id: 0,
    children: {},
    deleted_widgets: [],
    groupList:[]    
  }
};

function UpdateScreen($scope, $compile, json) {
  
	console.log(" ,,,,,,,,,,,,, "+angular.toJson(json));
  /* set id */
  nodeTree['screen']['id'] = json['id'];
  	
  /* set activity_id */
  nodeTree['screen']['activity_id'] = json['activity_id'];
  $scope.activity_id = json['activity_id'];
  
  /* set name */
  nodeTree['screen']['name'] = json['name'];
  $scope.screen_name = json['name'];

  /* set label */
  nodeTree['screen']['label'] = json['label'];
  $scope.screen_label = json['label'];

  /* set language */
  nodeTree['screen']['human_language_id'] = json['human_language_id'];
  $scope.screen_language = $scope.languages[~~json['human_language_id'] - 1];
  
  /*Rashmi*/
  nodeTree['screen']['wizard_id'] = json['wizard_id'];
  $scope.screen_wizard = json['wizard_id'];
  
  /* set description */
  nodeTree['screen']['description'] = json['description'];
  $scope.screen_description = json['description'];

  /*SureshAnand*/
    /* set role */
    nodeTree['screen']['role'] = json['role'];
    $scope.screen_role = json['role'];
  
  /* set notes */
  nodeTree['screen']['notes'] = json['notes'];
  /* TODO: setHTML from json['notes'] */
  /* Boopathi */
  $scope.screen_notes = json['notes'];

  /* set type */
  nodeTree['screen']['type'] = json['type'];
  /* TODO: show screen type */

  /* set width */
  nodeTree['screen']['width'] = json['width'];

  /* set height */
  nodeTree['screen']['height'] = json['height'];

  /* set client device information */
  nodeTree['screen']['client_device_type_id'] = json['client_device_type_id'];
  nodeTree['screen']['client_device_type'] = json['client_device_type'];
  nodeTree['screen']['client_device_type_name'] = json['client_device_type_name'];
  nodeTree['screen']['client_device_type_label'] = json['client_device_type_label'];
  nodeTree['screen']['client_device_type_description'] = json['client_device_type_description'];
  nodeTree['screen']['client_device_screen_size'] = json['client_device_screen_size'];
  nodeTree['screen']['client_device_resolution'] = json['client_device_resolution'];
  nodeTree['screen']['client_device_ppcm'] = json['client_device_ppcm'];
  nodeTree['screen']['client_device_type_os_name'] = json['client_device_type_os_name'];
  nodeTree['screen']['client_device_type_os_version'] = json['client_device_type_os_version'];

  /* set portrait image name */
  nodeTree['screen']['portrait_image_name'] = json['portrait_image_name'];

  /* set landscape image name */
  nodeTree['screen']['landscape_image_name'] = json['landscape_image_name'];

  /* set orientation */
  window.orientation = json['orientation'];
  switch (window.orientation) {
    case 'portrait':
      $('#landscape').removeClass('active');
      $('#portrait').addClass('active');

      break;
    case 'landscape':
      $('#portrait').removeClass('active');
      $('#landscape').addClass('active');

      break;
  }
  changeOrientation(window.orientation);

  /* set orientation locked */
  nodeTree['screen']['orientation_locked'] = json['orientation_locked'];

  /* set client language type */
  nodeTree['screen']['client_language_type'] = json['client_language_type'];

  /* set client library type */
  nodeTree['screen']['client_library_type'] = json['client_library_type'];

  /* set activity id */
  nodeTree['screen']['activity_id'] = json['activity_id'];

  /* set project id */
  nodeTree['screen']['projectid'] = json['projectid'];

  /* set primary noun id */
  nodeTree['screen']['primary_noun_id'] = json['primary_noun_id'];
  /* TODO: set $scope variable for primary noun */

  /* set secondary noun ids */
  nodeTree['screen']['secondary_noun_ids'] = (json['secondary_noun_ids']) ? json['secondary_noun_ids'] : [];
  /* TODO: set $scope variable for secondary nouns */

  /* set screen event_verb_combo */
  nodeTree['screen']['event_verb_combo'] = (json['event_verb_combo']) ? json['event_verb_combo'] : '';

  /* clean deleted widgets */
  nodeTree['screen']['deleted_widgets'] = [];
  
  nodeTree['screen']['checkboxGroupList'] = (json['checkboxGroupList']) ? json['checkboxGroupList'] : [];
  nodeTree['screen']['radioGroupList'] = (json['radioGroupList']) ? json['radioGroupList'] : [];

  /* check screen type */
  switch ($scope.name) {
    case 'mobile':
      showMobileScreen($scope, $compile, json);

      break;
    case 'tablet':
      showTabletScreen($scope, $compile, json);

      break;
    case 'desktop':
      showDesktopScreen($scope, $compile, json);

      break;
  }

  /* increment id for future elements */
  if (maxID(nodeTree['screen']['children']) == -1) {
    /* act as create */
    window.id = 0;

    /* fix for children === undefined */
    nodeTree['screen']['children'] = {};
  } else window.id = maxID(nodeTree['screen']['children']) + 1;
}

/* update desktop components */
function showDesktopLevel($scope, $compile, id, parent, type, width, height, offsetX, offsetY, rows, columns) {
  var html, label;

  switch (type) {
    case 'label':
      label = find(id, nodeTree['screen']['children'])['label'];
      html = '<div class="resizable" id="' + id + '"> \
						<label data-role="label" class="_Gp_element" id="' + (Number(id) - 1) + '">' + label + '</label> \
					</div>';
      $('#' + parent).append(html);

      new Resizable($('#' + id));
      new DragObject($('#' + id)[0]);

      $('#' + id).css({
        position: 'absolute',
        width: width,
        height: height
      }).offset({
        left: Math.round($('#' + parent).offset().left) + offsetX,
        top: Math.round($('#' + parent).offset().top) + offsetY
      });

      break;
    case 'button':
      label = find(id, nodeTree['screen']['children'])['label'];
      html = '<div class="resizable" id="' + id + '"> \
						<button type="button" data-role="button" class="_Gp_element btn btn-default" id="' + (Number(id) - 1) + '">' + label + '</button> \
					</div>';
      $('#' + parent).append(html);

      new Resizable($('#' + id));
      new DragObject($('#' + id)[0]);

      $('#' + id).css({
        position: 'absolute',
        width: width,
        height: height
      }).offset({
        left: Math.round($('#' + parent).offset().left) + offsetX,
        top: Math.round($('#' + parent).offset().top) + offsetY
      });

      break;
    case 'checkbox':
      label = find(id, nodeTree['screen']['children'])['label'];
      html = '<div class="resizable" id="' + id + '"> \
                        <label class="_Gp_element form-checkbox" data-role="checkbox" id="' + (Number(id) - 1) + '"> \
                            <input type="checkbox"> \
                            <span class="text">' + label + '</span> \
                        </label> \
                     </div>';
      $('#' + parent).append(html);

      new Resizable($('#' + id));
      new DragObject($('#' + id)[0]);

      $('#' + id).css({
        position: 'absolute',
        width: width,
        height: height
      }).offset({
        left: Math.round($('#' + parent).offset().left) + offsetX,
        top: Math.round($('#' + parent).offset().top) + offsetY
      });

      break;
    case 'radio':
      label = find(id, nodeTree['screen']['children'])['label'];
      html = '<div class="resizable" id="' + id + '"> \
                        <label class="_Gp_element form-radio" data-role="radio" id="' + (Number(id) - 1) + '"> \
                            <input type="radio"> \
                            <span class="text">' + label + '</span> \
                        </label> \
                     </div>';
      $('#' + parent).append(html);

      new Resizable($('#' + id));
      new DragObject($('#' + id)[0]);

      $('#' + id).css({
        position: 'absolute',
        width: width,
        height: height
      }).offset({
        left: Math.round($('#' + parent).offset().left) + offsetX,
        top: Math.round($('#' + parent).offset().top) + offsetY
      });

      break;
    case 'input':
      html = '<div class="resizable" id="' + id + '"> \
						<input data-role="input" type="text" class="_Gp_element form-control" id="' + (Number(id) - 1) + '"> \
					</div>';
      $('#' + parent).append(html);

      new Resizable($('#' + id));
      new DragObject($('#' + id)[0]);

      $('#' + id).css({
        position: 'absolute',
        width: width,
        height: height
      }).offset({
        left: Math.round($('#' + parent).offset().left) + offsetX,
        top: Math.round($('#' + parent).offset().top) + offsetY
      });

      break;
    case 'date':
      html = '<div class="resizable" id="' + id + '"> \
						<date-picker id="' + (~~id - 1) + '"> \
					</div>';
      $('#' + parent).append($compile(html)($scope));

      new Resizable($('#' + id));
      new DragObject($('#' + id)[0]);

      $('#' + id).css({
        position: 'absolute',
        width: width,
        height: height
      }).offset({
        left: Math.round($('#' + parent).offset().left) + offsetX,
        top: Math.round($('#' + parent).offset().top) + offsetY
      });

      break;
    case 'select':
      html = '<div class="resizable" id="' + id + '"> \
						<select data-role="select" class="_Gp_element form-control" id="' + (Number(id) - 1) + '"></select> \
					</div>';
      $('#' + parent).append(html);

      new Resizable($('#' + id));
      new DragObject($('#' + id)[0]);

      $('#' + id).css({
        position: 'absolute',
        width: width,
        height: height
      }).offset({
        left: Math.round($('#' + parent).offset().left) + offsetX,
        top: Math.round($('#' + parent).offset().top) + offsetY
      });

      break;
    case 'rich-text-editor':
      html = '<div class="resizable" id="' + id + '"> \
						<quill id="' + (Number(id) - 1) + '"> \
					</div>';
      $('#' + parent).append($compile(html)($scope));

      new Resizable($('#' + id));
      new DragObject($('#' + id)[0]);

      $('#' + id).css({
        position: 'absolute',
        width: width,
        height: height
      }).offset({
        left: Math.round($('#' + parent).offset().left) + offsetX,
        top: Math.round($('#' + parent).offset().top) + offsetY
      });

      break;
    case 'table':
      html = '<div class="resizable" id="' + id + '"> \
						<table data-role="table" class="_Gp_element table table-bordered" id="' + (~~id - 1) + '"> \
                            <thead> \
                                <tr>';
      for (var k = 0; k < columns.length; ++k) {
        if (columns[k]['width']) {
          html += '<th width="' + columns[k]['width'] + '%">' + columns[k]['label'] + '</th>';
        } else html += '<th>' + columns[k]['label'] + '</th>';
      }
      html += '</tr> \
                            </thead> \
                            <tbody>';
      for (var i = 0; i < rows - 1; ++i) {
        html += '<tr>';
        for (var j = 0; j < columns.length; ++j) {
          html += '<td></td>';
        }
        html += '</tr>';
      }
      html += '</tbody> \
                        </table> \
                    </div>';
      $('#' + parent).append(html);

      new Resizable($('#' + id));
      new DragObject($('#' + id)[0]);

      $('#' + id).css({
        position: 'absolute',
        width: width,
        height: height
      }).offset({
        left: Math.round($('#' + parent).offset().left) + offsetX,
        top: Math.round($('#' + parent).offset().top) + offsetY
      });

      break;
    case 'hrule':
      html = '<div class="resizable" id="' + id + '"> \
						<hr data-role="hrule" class="_Gp_element horizontal-line" id="' + (~~id - 1) + '"> \
					</div>';
      $('#' + parent).append(html);

      new Resizable($('#' + id));
      new DragObject($('#' + id)[0]);

      $('#' + id).css({
        position: 'absolute',
        width: width,
        height: height
      }).offset({
        left: Math.round($('#' + parent).offset().left) + offsetX,
        top: Math.round($('#' + parent).offset().top) + offsetY
      });

      break;
    case 'vrule':
      html = '<div class="resizable" id="' + id + '"> \
						<hr data-role="vrule" class="_Gp_element vertical-line" id="' + (Number(id) - 1) + '"> \
					</div>';
      $('#' + parent).append(html);

      new Resizable($('#' + id));
      new DragObject($('#' + id)[0]);

      $('#' + id).css({
        position: 'absolute',
        width: width,
        height: height
      }).offset({
        left: Math.round($('#' + parent).offset().left) + offsetX,
        top: Math.round($('#' + parent).offset().top) + offsetY
      });

      break;
    /* Boopathi */
    case 'link':
      label = find(id, nodeTree['screen']['children'])['label'];
      html = '<div class="resizable" id="' + id + '"> \
						<a data-href="#" data-role="link" class="_Gp_element" id="' + (Number(id) - 1) + '">' + label + '</a> \
					</div>';
      $('#' + parent).append(html);

      new Resizable($('#' + id));
      new DragObject($('#' + id)[0]);

      $('#' + id).css({
        position: 'absolute',
        width: width,
        height: height
      }).offset({
        left: Math.round($('#' + parent).offset().left) + offsetX,
        top: Math.round($('#' + parent).offset().top) + offsetY
      });

      break;
    /* Boopathi */
    case 'image':
      html = '<div class="resizable" id="' + id + '"> \
						<div data-role="image" class="_Gp_element image" id="' + (Number(id) - 1) + '">\
							<img src="' + find(id, nodeTree['screen']['children'])['image_src'] + '" class="image-default">\
						</div>\
					</div>';
      $('#' + parent).append(html);

      new Resizable($('#' + id));
      new DragObject($('#' + id)[0]);

      $('#' + id).css({
        position: 'absolute',
        width: width,
        height: height
      }).offset({
        left: Math.round($('#' + parent).offset().left) + offsetX,
        top: Math.round($('#' + parent).offset().top) + offsetY
      });

      break;
  }
}

/* update desktop complex components */
function showDesktopScreen($scope, $compile, json) {
  var firstLevel, secondLevel, thirdLevel;
  var html, id, parent, type, width, height,
    offsetX, offsetY, parentOffsetX, parentOffsetY,
    rows, columns, panels, label;

  /* show elements in Axe */
  var children = json['children'] ? json['children'] : {};
  nodeTree['screen']['children'] = children ? children : {};

  for (firstLevel in children) {
    if (children.hasOwnProperty(firstLevel)) {
      id = firstLevel;
      parent = 'screen';
      type = children[firstLevel]['type'];
      width = children[firstLevel][window.orientation + '_width'];
      height = children[firstLevel][window.orientation + '_height'];
      offsetX = children[firstLevel][window.orientation + 'X'];
      offsetY = children[firstLevel][window.orientation + 'Y'];
      rows = children[firstLevel]['rows'];
      columns = children[firstLevel]['columns'];

      showDesktopLevel($scope, $compile, id, parent, type, width, height, offsetX, offsetY, rows, columns);
      switch (type) {
        case 'border-container':
          html = '<div class="resizable" id="' + id + '"> \
                    <div data-role="border-container" class="_Gp_element border-container" id="' + (Number(id) - 1) + '"></div> \
                  </div>';
          $('#' + parent).append(html);

          new Resizable($('#' + id));
          new DragObject($('#' + id)[0]);
          new DropTarget($('#' + id)[0]);

          $('#' + id).css({
            position: 'absolute',
            width: width,
            height: height
          }).offset({
            left: Math.round($('#' + parent).offset().left) + offsetX,
            top: Math.round($('#' + parent).offset().top) + offsetY
          });

          /* nested elements */
          if (!$.isEmptyObject(children[firstLevel]['children'])) {
            for (secondLevel in children[firstLevel]['children']) {
              if (children[firstLevel]['children'].hasOwnProperty(secondLevel)) {
                id = secondLevel;
                type = children[firstLevel]['children'][secondLevel]['type'];
                width = children[firstLevel]['children'][secondLevel][window.orientation + '_width'];
                height = children[firstLevel]['children'][secondLevel][window.orientation + '_height'];
                parentOffsetX = children[firstLevel]['children'][secondLevel][window.orientation + 'OffsetX'] + children[firstLevel][window.orientation + 'X'];
                parentOffsetY = children[firstLevel]['children'][secondLevel][window.orientation + 'OffsetY'] + children[firstLevel][window.orientation + 'Y'];
                rows = children[firstLevel]['children'][secondLevel]['rows'];
                columns = children[firstLevel]['children'][secondLevel]['columns'];

                showDesktopLevel($scope, $compile, id, parent, type, width, height, parentOffsetX, parentOffsetY, rows, columns);
              }
            }
          }

          break;
        /* Boopathi */
        /* update name and label */
        /* here to make first tab is active - panels[0] is added */
        case 'tab':
          panels = Object.keys(nodeTree['screen']['children'][id]['children']);
          html = '<div class="resizable" id="' + id + '"> \
									<div data-role="tab" id="' + (Number(id) - (panels.length + 1)) + '" class="_Gp_element tab"> \
										<ul data-role="tab-list" class="tab-list nav nav-tabs"> \
											<li class="active"> \
												<a data-ng-href="#' + panels[0] + '">' + find(panels[0], nodeTree['screen']['children'][id]['children'])['label'] + '</a> \
											</li>';
          for (var i = 1; i < panels.length; i++) {
            html += '<li><a data-ng-href="#' + panels[i] + '">' + find(panels[i], nodeTree['screen']['children'][id]['children'])['label'] + '</a></li>';
          }
          html += '</ul> \
		   								 <div data-role="tab-content" class="tab-content"> \
		   									<div data-role="section" class="section active" id="' + panels[0] + '"></div>';
          for (var i = 1; i < panels.length; i++) {
            html += '<div data-role="section" class="section" id="' + panels[i] + '"></div>';
          }
          html += '</div> \
									</div> \
								</div>';
          $('#' + parent).append(html);

          new Resizable($('#' + id));
          new DragObject($('#' + id)[0]);
          $('#' + id).find('.section').each(function (index, panel) {
            new DropTarget(panel);
          });

          $('#' + id).css({
            position: 'absolute',
            width: width,
            height: height
          }).offset({
            left: Math.round($('#' + parent).offset().left) + offsetX,
            top: Math.round($('#' + parent).offset().top) + offsetY
          });

          for (secondLevel in nodeTree['screen']['children'][firstLevel]['children']) {
            if (nodeTree['screen']['children'][firstLevel]['children'].hasOwnProperty(secondLevel)) {

              /* nested elements */
              if (!$.isEmptyObject(nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'])) {
                for (thirdLevel in nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children']) {
                  if (nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'].hasOwnProperty(thirdLevel)) {
                    id = thirdLevel;
                    type = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel]['type'];
                    width = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel][window.orientation + '_width'];
                    height = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel][window.orientation + '_height'];
                    parentOffsetX = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel][window.orientation + 'OffsetX'] + children[firstLevel][window.orientation + 'X'];
                    parentOffsetY = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel][window.orientation + 'OffsetY'] + children[firstLevel][window.orientation + 'Y'];
                    rows = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel]['rows'];
                    columns = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel]['columns'];

                    showDesktopLevel($scope, $compile, id, parent, type, width, height, parentOffsetX, parentOffsetY, rows, columns);
                  }
                }
              }
            }
          }

          /* show children of current tab panel and hide siblings children */
          var tab = $('#' + panels[0]).parents('.resizable').attr('id');

          if (nodeTree['screen']['children'][tab].hasOwnProperty('children')) {
            for (firstLevel in nodeTree['screen']['children'][tab]['children']) {
              if (nodeTree['screen']['children'][tab]['children'].hasOwnProperty(firstLevel)) {
                if (nodeTree['screen']['children'][tab]['children'][firstLevel].hasOwnProperty('children')) {
                  if (firstLevel === panels[0]) {
                    for (secondLevel in nodeTree['screen']['children'][tab]['children'][firstLevel]['children']) {
                      if (nodeTree['screen']['children'][tab]['children'][firstLevel]['children'].hasOwnProperty(secondLevel)) {
                        $('#' + secondLevel).show();
                      }
                    }
                  } else {
                    for (secondLevel in nodeTree['screen']['children'][tab]['children'][firstLevel]['children']) {
                      if (nodeTree['screen']['children'][tab]['children'][firstLevel]['children'].hasOwnProperty(secondLevel)) {
                        $('#' + secondLevel).hide();
                      }
                    }
                  }
                }
              }
            }
          }

          break;
        /* Boopathi */
        /* update name and label */
        /* here to make first panel is active panels[0] is added */
        case 'accordion':
          panels = Object.keys(nodeTree['screen']['children'][id]['children']);
          html = '<div class="resizable" id="' + id + '"> \
								<div data-role="accordion" class="_Gp_element accordion panel-group" id="' + (~~id - (panels.length + 1)) + '"> \
									<div class="panel panel-default active"> \
										<div data-role="panel-heading" class="panel-heading"> \
											<h4 class="panel-title"> \
												<a data-ng-href="#' + panels[0] + '">' + find(panels[0], nodeTree['screen']['children'][id]['children'])['label'] + '</a> \
											</h4> \
										</div> \
										<div data-role="section" class="section" id="' + panels[0] + '"></div> \
									</div>';
          for (var i = 1; i < panels.length; i++) {
            html += '<div class="panel panel-default"> \
											<div data-role="panel-heading" class="panel-heading"> \
												<h4 class="panel-title"> \
													<a data-ng-href="#' + panels[i] + '">' + find(panels[i], nodeTree['screen']['children'][id]['children'])['label'] + '</a> \
												</h4> \
											</div> \
											<div data-role="section" class="section" id="' + panels[i] + '"></div> \
										</div>';
          }
          html += '</div> \
							</div>';
          $('#' + parent).append(html);

          new Resizable($('#' + id));
          new DragObject($('#' + id)[0]);
          $('#' + id).find('.section').each(function (index, panel) {
            new DropTarget(panel);
          });

          $('#' + id).css({
            position: 'absolute',
            width: width,
            height: height
          }).offset({
            left: Math.round($('#' + parent).offset().left) + offsetX,
            top: Math.round($('#' + parent).offset().top) + offsetY
          });

          for (secondLevel in nodeTree['screen']['children'][firstLevel]['children']) {
            if (nodeTree['screen']['children'][firstLevel]['children'].hasOwnProperty(secondLevel)) {
              /* set accordion panels height */
              //$('#' + secondLevel).css('height', nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['height']);
              $('#' + secondLevel).css('height', nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['portrait_height']);

              /* nested elements */
              if (!$.isEmptyObject(nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'])) {
                for (thirdLevel in nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children']) {
                  if (nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'].hasOwnProperty(thirdLevel)) {
                    id = thirdLevel;
                    type = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel]['type'];
                    width = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel][window.orientation + '_width'];
                    height = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel][window.orientation + '_height'];
                    parentOffsetX = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel][window.orientation + 'OffsetX'] + children[firstLevel][window.orientation + 'X'];
                    parentOffsetY = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel][window.orientation + 'OffsetY'] + children[firstLevel][window.orientation + 'Y'];
                    rows = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel]['rows'];
                    columns = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel]['columns'];

                    showDesktopLevel($scope, $compile, id, parent, type, width, height, parentOffsetX, parentOffsetY, rows, columns);
                  }
                }
              }
            }
          }

          /* show children of current accordion panel and hide siblings children */
          var accordion = $('#' + panels[0]).parents('.resizable').attr('id');

          if (nodeTree['screen']['children'][accordion].hasOwnProperty('children')) {
            for (firstLevel in nodeTree['screen']['children'][accordion]['children']) {
              if (nodeTree['screen']['children'][accordion]['children'].hasOwnProperty(firstLevel)) {
                if (nodeTree['screen']['children'][accordion]['children'][firstLevel].hasOwnProperty('children')) {
                  if (firstLevel === panels[0] && $('#' + panels[0]).parents('.panel').hasClass('active')) {
                    for (secondLevel in nodeTree['screen']['children'][accordion]['children'][firstLevel]['children']) {
                      if (nodeTree['screen']['children'][accordion]['children'][firstLevel]['children'].hasOwnProperty(secondLevel)) {
                        $('#' + secondLevel).show();
                      }
                    }
                  } else {
                    for (secondLevel in nodeTree['screen']['children'][accordion]['children'][firstLevel]['children']) {
                      if (nodeTree['screen']['children'][accordion]['children'][firstLevel]['children'].hasOwnProperty(secondLevel)) {
                        $('#' + secondLevel).hide();
                      }
                    }
                  }
                }
              }
            }
          }

          break;
        case 'panel':
          label = find(id, nodeTree['screen']['children'])['label'];
          html = '<div class="resizable" id="' + Number(id) + '"> \
								<div data-role="panel" class="_Gp_element panel panel-default" id="' + (Number(id) - 1) + '"> \
									<div data-role="panel-heading" class="panel-heading"> \
										<h3 class="panel-title">' + label + '</h3> \
									</div> \
									<div data-role="panel-content" class="panel-body" id="' + (Number(id) - 2) + '"></div> \
								</div> \
							</div>';
          $('#' + parent).append(html);

          new Resizable($('#' + id));
          new DragObject($('#' + id)[0]);
          new DropTarget($('#' + id).find('.panel-body')[0]);

          $('#' + id).css({
            position: 'absolute',
            width: width,
            height: height
          }).offset({
            left: Math.round($('#' + parent).offset().left) + offsetX,
            top: Math.round($('#' + parent).offset().top) + offsetY
          });

          /* nested elements */
          if (!$.isEmptyObject(nodeTree['screen']['children'][firstLevel]['children'])) {
            for (secondLevel in nodeTree['screen']['children'][firstLevel]['children']) {
              if (nodeTree['screen']['children'][firstLevel]['children'].hasOwnProperty(secondLevel)) {
                id = secondLevel;
                type = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['type'];
                width = nodeTree['screen']['children'][firstLevel]['children'][secondLevel][window.orientation + '_width'];
                height = nodeTree['screen']['children'][firstLevel]['children'][secondLevel][window.orientation + '_height'];
                parentOffsetX = nodeTree['screen']['children'][firstLevel]['children'][secondLevel][window.orientation + 'OffsetX'] + children[firstLevel][window.orientation + 'X'];
                parentOffsetY = nodeTree['screen']['children'][firstLevel]['children'][secondLevel][window.orientation + 'OffsetY'] + children[firstLevel][window.orientation + 'Y'];
                rows = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['rows'];
                columns = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['columns'];

                showDesktopLevel($scope, $compile, id, parent, type, width, height, parentOffsetX, parentOffsetY, rows, columns);
              }
            }
          }

          break;
      }
    }
  }
}

/* update tablet components */
function showTabletLevel($scope, $compile, id, parent, type, width, height, offsetX, offsetY, rows, columns) {
  var html, label;

  switch (type) {
    case 'label':
      label = find(id, nodeTree['screen']['children'])['label'];
      html = '<div class="resizable" id="' + id + '"> \
                <label data-role="label" class="_Gp_element" id="' + (Number(id) - 1) + '">' + label + '</label> \
              </div>';
      $('#' + parent).append(html);

      new Resizable($('#' + id));
      new DragObject($('#' + id)[0]);

      $('#' + id).css({
        position: 'absolute',
        width: width,
        height: height
      }).offset({
        left: Math.round($('#' + parent).offset().left) + offsetX,
        top: Math.round($('#' + parent).offset().top) + offsetY
      });

      break;
    case 'button':
      label = find(id, nodeTree['screen']['children'])['label'];
      html = '<div class="resizable" id="' + id + '"> \
                <button type="button" data-role="button" class="_Gp_element btn btn-default" id="' + (Number(id) - 1) + '">' + label + '</button> \
              </div>';
      $('#' + parent).append(html);

      new Resizable($('#' + id));
      new DragObject($('#' + id)[0]);

      $('#' + id).css({
        position: 'absolute',
        width: width,
        height: height
      }).offset({
        left: Math.round($('#' + parent).offset().left) + offsetX,
        top: Math.round($('#' + parent).offset().top) + offsetY
      });

      break;
    case 'camera':
      label = find(id, nodeTree['screen']['children'])['label'];
      html = '<div class="resizable" id="' + id + '"> \
                <button type="button" data-role="camera" class="_Gp_element ionic button button-light" id="' + (Number(id) - 1) + '"> \
                  <i class="fa fa-camera"></i> \
                  <span>' + label + '</span> \
                </button> \
              </div>';
      $('#' + parent).append(html);

      new Resizable($('#' + id));
      new DragObject($('#' + id)[0]);

      $('#' + id).css({
        position: 'absolute',
        width: width,
        height: height
      }).offset({
        left: Math.round($('#' + parent).offset().left) + offsetX,
        top: Math.round($('#' + parent).offset().top) + offsetY
      });

      break;
    
    case 'select':
        html = '<div class="resizable" id="' + id + '"> \
  						<select data-role="select" class="_Gp_element form-control" id="' + (Number(id) - 1) + '"></select> \
  					</div>';
        $('#' + parent).append(html);

        new Resizable($('#' + id));
        new DragObject($('#' + id)[0]);

        $('#' + id).css({
          position: 'absolute',
          width: width,
          height: height
        }).offset({
          left: Math.round($('#' + parent).offset().left) + offsetX,
          top: Math.round($('#' + parent).offset().top) + offsetY
        });

        break;

    case 'recorder':
        label = find(id, nodeTree['screen']['children'])['label'];        
        html = '<div class="resizable" id="' + id + '"> \
		<img src="app/img/icons/ionic/record.png" class="image-default" id="' + (Number(id) - 1) + '">\
		</div>';
        $('#' + parent).append(html);

        new Resizable($('#' + id));
        new DragObject($('#' + id)[0]);

        $('#' + id).css({
          position: 'absolute',
          width: width,
          height: height
        }).offset({
          left: Math.round($('#' + parent).offset().left) + offsetX,
          top: Math.round($('#' + parent).offset().top) + offsetY
        });

        break;  
    
    case 'video':
        label = find(id, nodeTree['screen']['children'])['label'];        
        html = '<div class="resizable" id="' + id + '"> \
		<img src="app/img/icons/ionic/video.png" class="image-default" id="' + (Number(id) - 1) + '">\
		</div>';
        $('#' + parent).append(html);

        new Resizable($('#' + id));
        new DragObject($('#' + id)[0]);

        $('#' + id).css({
          position: 'absolute',
          width: width,
          height: height
        }).offset({
          left: Math.round($('#' + parent).offset().left) + offsetX,
          top: Math.round($('#' + parent).offset().top) + offsetY
        });

        break;      
    case 'checkbox':
      label = find(id, nodeTree['screen']['children'])['label'];
      html = '<div class="resizable" id="' + id + '"> \
                <label class="_Gp_element form-checkbox" data-role="checkbox" id="' + (Number(id) - 1) + '"> \
                    <input type="checkbox"> \
                    <span class="text">' + label + '</span> \
                </label> \
              </div>';
      $('#' + parent).append(html);

      new Resizable($('#' + id));
      new DragObject($('#' + id)[0]);

      $('#' + id).css({
        position: 'absolute',
        width: width,
        height: height
      }).offset({
        left: Math.round($('#' + parent).offset().left) + offsetX,
        top: Math.round($('#' + parent).offset().top) + offsetY
      });

      break;
    case 'radio':
      label = find(id, nodeTree['screen']['children'])['label'];
      html = '<div class="resizable" id="' + id + '"> \
                        <label class="_Gp_element form-radio" data-role="radio" id="' + (Number(id) - 1) + '"> \
                            <input type="radio"> \
                            <span class="text">' + label + '</span> \
                        </label> \
                     </div>';
      $('#' + parent).append(html);

      new Resizable($('#' + id));
      new DragObject($('#' + id)[0]);

      $('#' + id).css({
        position: 'absolute',
        width: width,
        height: height
      }).offset({
        left: Math.round($('#' + parent).offset().left) + offsetX,
        top: Math.round($('#' + parent).offset().top) + offsetY
      });

      break;
    case 'input':
      html = '<div class="resizable" id="' + id + '"> \
						<input data-role="input" type="text" class="_Gp_element form-control" id="' + (Number(id) - 1) + '"> \
					</div>';
      $('#' + parent).append(html);

      new Resizable($('#' + id));
      new DragObject($('#' + id)[0]);

      $('#' + id).css({
        position: 'absolute',
        width: width,
        height: height
      }).offset({
        left: Math.round($('#' + parent).offset().left) + offsetX,
        top: Math.round($('#' + parent).offset().top) + offsetY
      });

      break;
    case 'date':
      html = '<div class="resizable" id="' + id + '"> \
						<date-picker id="' + (~~id - 1) + '"> \
					</div>';
      $('#' + parent).append($compile(html)($scope));

      new Resizable($('#' + id));
      new DragObject($('#' + id)[0]);

      $('#' + id).css({
        position: 'absolute',
        width: width,
        height: height
      }).offset({
        left: Math.round($('#' + parent).offset().left) + offsetX,
        top: Math.round($('#' + parent).offset().top) + offsetY
      });

      break;
    case 'select':
      html = '<div class="resizable" id="' + id + '"> \
						<select data-role="select" class="_Gp_element form-control" id="' + (Number(id) - 1) + '"></select> \
					</div>';
      $('#' + parent).append(html);

      new Resizable($('#' + id));
      new DragObject($('#' + id)[0]);

      $('#' + id).css({
        position: 'absolute',
        width: width,
        height: height
      }).offset({
        left: Math.round($('#' + parent).offset().left) + offsetX,
        top: Math.round($('#' + parent).offset().top) + offsetY
      });

      break;
    case 'rich-text-editor':
      html = '<div class="resizable" id="' + id + '"> \
						<quill id="' + (Number(id) - 1) + '"> \
					</div>';
      $('#' + parent).append($compile(html)($scope));

      new Resizable($('#' + id));
      new DragObject($('#' + id)[0]);

      $('#' + id).css({
        position: 'absolute',
        width: width,
        height: height
      }).offset({
        left: Math.round($('#' + parent).offset().left) + offsetX,
        top: Math.round($('#' + parent).offset().top) + offsetY
      });

      break;
    case 'table':
      html = '<div class="resizable" id="' + id + '"> \
						<table data-role="table" class="_Gp_element table table-bordered" id="' + (~~id - 1) + '"> \
                            <thead> \
                                <tr>';
      for (var k = 0; k < columns.length; ++k) {
        if (columns[k]['width']) {
          html += '<th width="' + columns[k]['width'] + '%">' + columns[k]['label'] + '</th>';
        } else html += '<th>' + columns[k]['label'] + '</th>';
      }
      html += '</tr> \
                            </thead> \
                            <tbody>';
      for (var i = 0; i < rows - 1; ++i) {
        html += '<tr>';
        for (var j = 0; j < columns.length; ++j) {
          html += '<td></td>';
        }
        html += '</tr>';
      }
      html += '</tbody> \
                        </table> \
                    </div>';
      $('#' + parent).append(html);

      new Resizable($('#' + id));
      new DragObject($('#' + id)[0]);

      $('#' + id).css({
        position: 'absolute',
        width: width,
        height: height
      }).offset({
        left: Math.round($('#' + parent).offset().left) + offsetX,
        top: Math.round($('#' + parent).offset().top) + offsetY
      });

      break;
    case 'hrule':
      html = '<div class="resizable" id="' + id + '"> \
						<hr data-role="hrule" class="_Gp_element horizontal-line" id="' + (~~id - 1) + '"> \
					</div>';
      $('#' + parent).append(html);

      new Resizable($('#' + id));
      new DragObject($('#' + id)[0]);

      $('#' + id).css({
        position: 'absolute',
        width: width,
        height: height
      }).offset({
        left: Math.round($('#' + parent).offset().left) + offsetX,
        top: Math.round($('#' + parent).offset().top) + offsetY
      });

      break;
    case 'vrule':
      html = '<div class="resizable" id="' + id + '"> \
						<hr data-role="vrule" class="_Gp_element vertical-line" id="' + (Number(id) - 1) + '"> \
					</div>';
      $('#' + parent).append(html);

      new Resizable($('#' + id));
      new DragObject($('#' + id)[0]);

      $('#' + id).css({
        position: 'absolute',
        width: width,
        height: height
      }).offset({
        left: Math.round($('#' + parent).offset().left) + offsetX,
        top: Math.round($('#' + parent).offset().top) + offsetY
      });

      break;
    /* Boopathi */
    case 'link':
      label = find(id, nodeTree['screen']['children'])['label'];
      html = '<div class="resizable" id="' + id + '"> \
						<a data-href="#" data-role="link" class="_Gp_element" id="' + (Number(id) - 1) + '">' + label + '</a> \
					</div>';
      $('#' + parent).append(html);

      new Resizable($('#' + id));
      new DragObject($('#' + id)[0]);

      $('#' + id).css({
        position: 'absolute',
        width: width,
        height: height
      }).offset({
        left: Math.round($('#' + parent).offset().left) + offsetX,
        top: Math.round($('#' + parent).offset().top) + offsetY
      });

      break;
    /* Boopathi */
    case 'image':
      html = '<div class="resizable" id="' + id + '"> \
						<div data-role="image" class="_Gp_element image" id="' + (Number(id) - 1) + '">\
							<img src="' + find(id, nodeTree['screen']['children'])['image_src'] + '" class="image-default">\
						</div>\
					</div>';
      $('#' + parent).append(html);

      new Resizable($('#' + id));
      new DragObject($('#' + id)[0]);

      $('#' + id).css({
        position: 'absolute',
        width: width,
        height: height
      }).offset({
        left: Math.round($('#' + parent).offset().left) + offsetX,
        top: Math.round($('#' + parent).offset().top) + offsetY
      });

      break;
  }
}

/* update tablet complex components */
function showTabletScreen($scope, $compile, json) {
  var firstLevel, secondLevel, thirdLevel;
  var html, id, parent, type, width, height,
    offsetX, offsetY, parentOffsetX, parentOffsetY,
    rows, columns, panels, label;

  /* show elements in Axe */
  var children = json['children'] ? json['children'] : {};
  nodeTree['screen']['children'] = children ? children : {};

  for (firstLevel in children) {
    if (children.hasOwnProperty(firstLevel)) {
      id = firstLevel;
      parent = 'screen';
      type = children[firstLevel]['type'];
      width = children[firstLevel][window.orientation + '_width'];
      height = children[firstLevel][window.orientation + '_height'];
      offsetX = children[firstLevel][window.orientation + 'X'];
      offsetY = children[firstLevel][window.orientation + 'Y'];
      rows = children[firstLevel]['rows'];
      columns = children[firstLevel]['columns'];

      showTabletLevel($scope, $compile, id, parent, type, width, height, offsetX, offsetY, rows, columns);
      switch (type) {
        case 'border-container':
          html = '<div class="resizable" id="' + id + '"> \
								<div data-role="border-container" class="_Gp_element border-container" id="' + (Number(id) - 1) + '"></div> \
							</div>';
          $('#' + parent).append(html);

          new Resizable($('#' + id));
          new DragObject($('#' + id)[0]);
          new DropTarget($('#' + id)[0]);

          $('#' + id).css({
            position: 'absolute',
            width: width,
            height: height
          }).offset({
            left: Math.round($('#' + parent).offset().left) + offsetX,
            top: Math.round($('#' + parent).offset().top) + offsetY
          });

          /* nested elements */
          if (!$.isEmptyObject(children[firstLevel]['children'])) {
            for (secondLevel in children[firstLevel]['children']) {
              if (children[firstLevel]['children'].hasOwnProperty(secondLevel)) {
                id = secondLevel;
                type = children[firstLevel]['children'][secondLevel]['type'];
                width = children[firstLevel]['children'][secondLevel][window.orientation + '_width'];
                height = children[firstLevel]['children'][secondLevel][window.orientation + '_height'];
                parentOffsetX = children[firstLevel]['children'][secondLevel][window.orientation + 'OffsetX'] + children[firstLevel][window.orientation + 'X'];
                parentOffsetY = children[firstLevel]['children'][secondLevel][window.orientation + 'OffsetY'] + children[firstLevel][window.orientation + 'Y'];
                rows = children[firstLevel]['children'][secondLevel]['rows'];
                columns = children[firstLevel]['children'][secondLevel]['columns'];

                showTabletLevel($scope, $compile, id, parent, type, width, height, parentOffsetX, parentOffsetY, rows, columns);
              }
            }
          }

          break;
        /* Boopathi */
        /* update name and label */
        /* here to make first tab is active - panels[0] is added */
        case 'tab':
          panels = Object.keys(nodeTree['screen']['children'][id]['children']);
          html = '<div class="resizable" id="' + id + '"> \
									<div data-role="tab" id="' + (Number(id) - (panels.length + 1)) + '" class="_Gp_element tab"> \
										<ul data-role="tab-list" class="tab-list nav nav-tabs"> \
											<li class="active"> \
												<a data-ng-href="#' + panels[0] + '">' + find(panels[0], nodeTree['screen']['children'][id]['children'])['label'] + '</a> \
											</li>';
          for (var i = 1; i < panels.length; i++) {
            html += '<li><a data-ng-href="#' + panels[i] + '">' + find(panels[i], nodeTree['screen']['children'][id]['children'])['label'] + '</a></li>';
          }
          html += '</ul> \
		   								 <div data-role="tab-content" class="tab-content"> \
		   									<div data-role="section" class="section active" id="' + panels[0] + '"></div>';
          for (var i = 1; i < panels.length; i++) {
            html += '<div data-role="section" class="section" id="' + panels[i] + '"></div>';
          }
          html += '</div> \
									</div> \
								</div>';
          $('#' + parent).append(html);

          new Resizable($('#' + id));
          new DragObject($('#' + id)[0]);
          $('#' + id).find('.section').each(function (index, panel) {
            new DropTarget(panel);
          });

          $('#' + id).css({
            position: 'absolute',
            width: width,
            height: height
          }).offset({
            left: Math.round($('#' + parent).offset().left) + offsetX,
            top: Math.round($('#' + parent).offset().top) + offsetY
          });

          for (secondLevel in nodeTree['screen']['children'][firstLevel]['children']) {
            if (nodeTree['screen']['children'][firstLevel]['children'].hasOwnProperty(secondLevel)) {

              /* nested elements */
              if (!$.isEmptyObject(nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'])) {
                for (thirdLevel in nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children']) {
                  if (nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'].hasOwnProperty(thirdLevel)) {
                    id = thirdLevel;
                    type = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel]['type'];
                    width = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel][window.orientation + '_width'];
                    height = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel][window.orientation + '_height'];
                    parentOffsetX = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel][window.orientation + 'OffsetX'] + children[firstLevel][window.orientation + 'X'];
                    parentOffsetY = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel][window.orientation + 'OffsetY'] + children[firstLevel][window.orientation + 'Y'];
                    rows = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel]['rows'];
                    columns = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel]['columns'];

                    showTabletLevel($scope, $compile, id, parent, type, width, height, parentOffsetX, parentOffsetY, rows, columns);
                  }
                }
              }
            }
          }

          /* show children of current tab panel and hide siblings children */
          var tab = $('#' + panels[0]).parents('.resizable').attr('id');

          if (nodeTree['screen']['children'][tab].hasOwnProperty('children')) {
            for (firstLevel in nodeTree['screen']['children'][tab]['children']) {
              if (nodeTree['screen']['children'][tab]['children'].hasOwnProperty(firstLevel)) {
                if (nodeTree['screen']['children'][tab]['children'][firstLevel].hasOwnProperty('children')) {
                  if (firstLevel === panels[0]) {
                    for (secondLevel in nodeTree['screen']['children'][tab]['children'][firstLevel]['children']) {
                      if (nodeTree['screen']['children'][tab]['children'][firstLevel]['children'].hasOwnProperty(secondLevel)) {
                        $('#' + secondLevel).show();
                      }
                    }
                  } else {
                    for (secondLevel in nodeTree['screen']['children'][tab]['children'][firstLevel]['children']) {
                      if (nodeTree['screen']['children'][tab]['children'][firstLevel]['children'].hasOwnProperty(secondLevel)) {
                        $('#' + secondLevel).hide();
                      }
                    }
                  }
                }
              }
            }
          }

          break;
        /* Boopathi */
        /* update name and label */
        /* here to make first panel is active panels[0] is added */
        case 'accordion':
          panels = Object.keys(nodeTree['screen']['children'][id]['children']);
          html = '<div class="resizable" id="' + id + '"> \
								<div data-role="accordion" class="_Gp_element accordion panel-group" id="' + (~~id - (panels.length + 1)) + '"> \
									<div class="panel panel-default active"> \
										<div data-role="panel-heading" class="panel-heading"> \
											<h4 class="panel-title"> \
												<a data-ng-href="#' + panels[0] + '">' + find(panels[0], nodeTree['screen']['children'][id]['children'])['label'] + '</a> \
											</h4> \
										</div> \
										<div data-role="section" class="section" id="' + panels[0] + '"></div> \
									</div>';
          for (var i = 1; i < panels.length; i++) {
            html += '<div class="panel panel-default"> \
											<div data-role="panel-heading" class="panel-heading"> \
												<h4 class="panel-title"> \
													<a data-ng-href="#' + panels[i] + '">' + find(panels[i], nodeTree['screen']['children'][id]['children'])['label'] + '</a> \
												</h4> \
											</div> \
											<div data-role="section" class="section" id="' + panels[i] + '"></div> \
										</div>';
          }
          html += '</div> \
							</div>';
          $('#' + parent).append(html);

          new Resizable($('#' + id));
          new DragObject($('#' + id)[0]);
          $('#' + id).find('.section').each(function (index, panel) {
            new DropTarget(panel);
          });

          $('#' + id).css({
            position: 'absolute',
            width: width,
            height: height
          }).offset({
            left: Math.round($('#' + parent).offset().left) + offsetX,
            top: Math.round($('#' + parent).offset().top) + offsetY
          });

          for (secondLevel in nodeTree['screen']['children'][firstLevel]['children']) {
            if (nodeTree['screen']['children'][firstLevel]['children'].hasOwnProperty(secondLevel)) {
              /* set accordion panels height */
              $('#' + secondLevel).css('height', nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['height']);

              /* nested elements */
              if (!$.isEmptyObject(nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'])) {
                for (thirdLevel in nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children']) {
                  if (nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'].hasOwnProperty(thirdLevel)) {
                    id = thirdLevel;
                    type = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel]['type'];
                    width = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel][window.orientation + '_width'];
                    height = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel][window.orientation + '_height'];
                    parentOffsetX = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel][window.orientation + 'OffsetX'] + children[firstLevel][window.orientation + 'X'];
                    parentOffsetY = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel][window.orientation + 'OffsetY'] + children[firstLevel][window.orientation + 'Y'];
                    rows = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel]['rows'];
                    columns = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel]['columns'];

                    showTabletLevel($scope, $compile, id, parent, type, width, height, parentOffsetX, parentOffsetY, rows, columns);
                  }
                }
              }
            }
          }

          /* show children of current accordion panel and hide siblings children */
          var accordion = $('#' + panels[0]).parents('.resizable').attr('id');

          if (nodeTree['screen']['children'][accordion].hasOwnProperty('children')) {
            for (firstLevel in nodeTree['screen']['children'][accordion]['children']) {
              if (nodeTree['screen']['children'][accordion]['children'].hasOwnProperty(firstLevel)) {
                if (nodeTree['screen']['children'][accordion]['children'][firstLevel].hasOwnProperty('children')) {
                  if (firstLevel === panels[0] && $('#' + panels[0]).parents('.panel').hasClass('active')) {
                    for (secondLevel in nodeTree['screen']['children'][accordion]['children'][firstLevel]['children']) {
                      if (nodeTree['screen']['children'][accordion]['children'][firstLevel]['children'].hasOwnProperty(secondLevel)) {
                        $('#' + secondLevel).show();
                      }
                    }
                  } else {
                    for (secondLevel in nodeTree['screen']['children'][accordion]['children'][firstLevel]['children']) {
                      if (nodeTree['screen']['children'][accordion]['children'][firstLevel]['children'].hasOwnProperty(secondLevel)) {
                        $('#' + secondLevel).hide();
                      }
                    }
                  }
                }
              }
            }
          }

          break;
        case 'panel':
          label = find(id, nodeTree['screen']['children'])['label'];
          html = '<div class="resizable" id="' + Number(id) + '"> \
								<div data-role="panel" class="_Gp_element panel panel-default" id="' + (Number(id) - 1) + '"> \
									<div data-role="panel-heading" class="panel-heading"> \
										<h3 class="panel-title">' + label + '</h3> \
									</div> \
									<div data-role="panel-content" class="panel-body" id="' + (Number(id) - 2) + '"></div> \
								</div> \
							</div>';
          $('#' + parent).append(html);

          new Resizable($('#' + id));
          new DragObject($('#' + id)[0]);
          new DropTarget($('#' + id).find('.panel-body')[0]);

          $('#' + id).css({
            position: 'absolute',
            width: width,
            height: height
          }).offset({
            left: Math.round($('#' + parent).offset().left) + offsetX,
            top: Math.round($('#' + parent).offset().top) + offsetY
          });

          /* nested elements */
          if (!$.isEmptyObject(nodeTree['screen']['children'][firstLevel]['children'])) {
            for (secondLevel in nodeTree['screen']['children'][firstLevel]['children']) {
              if (nodeTree['screen']['children'][firstLevel]['children'].hasOwnProperty(secondLevel)) {
                id = secondLevel;
                type = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['type'];
                width = nodeTree['screen']['children'][firstLevel]['children'][secondLevel][window.orientation + '_width'];
                height = nodeTree['screen']['children'][firstLevel]['children'][secondLevel][window.orientation + '_height'];
                parentOffsetX = nodeTree['screen']['children'][firstLevel]['children'][secondLevel][window.orientation + 'OffsetX'] + children[firstLevel][window.orientation + 'X'];
                parentOffsetY = nodeTree['screen']['children'][firstLevel]['children'][secondLevel][window.orientation + 'OffsetY'] + children[firstLevel][window.orientation + 'Y'];
                rows = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['rows'];
                columns = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['columns'];

                showTabletLevel($scope, $compile, id, parent, type, width, height, parentOffsetX, parentOffsetY, rows, columns);
              }
            }
          }

          break;
      }
    }
  }
}

/* update mobile components */
function showMobileLevel($scope, $compile, id, parent, type, width, height, offsetX, offsetY, rows, columns) {
  var html, label;

  switch (type) {
    case 'label':
      label = find(id, nodeTree['screen']['children'])['label'];
      html = '<div class="resizable" id="' + id + '"> \
						<label data-role="label" class="_Gp_element" id="' + (Number(id) - 1) + '">' + label + '</label> \
					</div>';
      $('#' + parent).append(html);

      new Resizable($('#' + id));
      new DragObject($('#' + id)[0]);

      $('#' + id).css({
        position: 'absolute',
        width: width,
        height: height
      }).offset({
        left: Math.round($('#' + parent).offset().left) + offsetX,
        top: Math.round($('#' + parent).offset().top) + offsetY
      });

      break;
    case 'button':
      label = find(id, nodeTree['screen']['children'])['label'];
      html = '<div class="resizable" id="' + id + '"> \
			            <button type="button" data-role="button" class="_Gp_element ionic button button-light" id="' + (Number(id) - 1) + '">' + label + '</button> \
			        </div>';
      $('#' + parent).append(html);

      new Resizable($('#' + id));
      new DragObject($('#' + id)[0]);

      $('#' + id).css({
        position: 'absolute',
        width: width,
        height: height
      }).offset({
        left: Math.round($('#' + parent).offset().left) + offsetX,
        top: Math.round($('#' + parent).offset().top) + offsetY
      });

      break;
    case 'camera':
      label = find(id, nodeTree['screen']['children'])['label'];
      html = '<div class="resizable" id="' + id + '"> \
                <button type="button" data-role="camera" class="_Gp_element ionic button button-light" id="' + (Number(id) - 1) + '"> \
                  <i class="fa fa-camera"></i> \
                  <span>' + label + '</span> \
                </button> \
              </div>';
      $('#' + parent).append(html);

      new Resizable($('#' + id));
      new DragObject($('#' + id)[0]);

      $('#' + id).css({
        position: 'absolute',
        width: width,
        height: height
      }).offset({
        left: Math.round($('#' + parent).offset().left) + offsetX,
        top: Math.round($('#' + parent).offset().top) + offsetY
      });

      break;
    case 'select':
        html = '<div class="resizable" id="' + id + '"> \
  						<select data-role="select" class="_Gp_element form-control" id="' + (Number(id) - 1) + '"></select> \
  					</div>';
        $('#' + parent).append(html);

        new Resizable($('#' + id));
        new DragObject($('#' + id)[0]);

        $('#' + id).css({
          position: 'absolute',
          width: width,
          height: height
        }).offset({
          left: Math.round($('#' + parent).offset().left) + offsetX,
          top: Math.round($('#' + parent).offset().top) + offsetY
        });

        break;


    case 'recorder':
        label = find(id, nodeTree['screen']['children'])['label'];
        html = '<div class="resizable" id="' + id + '"> \
        			<img src="app/img/icons/ionic/record.png" class="image-default" id="' + (Number(id) - 1) + '" data-role="recorder">\
        		</div>';
        
        $('#' + parent).append(html);

        new Resizable($('#' + id));
        new DragObject($('#' + id)[0]);

        $('#' + id).css({
          position: 'absolute',
          width: width,
          height: height
        }).offset({
          left: Math.round($('#' + parent).offset().left) + offsetX,
          top: Math.round($('#' + parent).offset().top) + offsetY
        });

        break;
    case 'video':
        label = find(id, nodeTree['screen']['children'])['label'];
        html = '<div class="resizable" id="' + id + '"> \
        			<img src="app/img/icons/ionic/video.png" class="image-default" id="' + (Number(id) - 1) + '" data-role="video">\
        		</div>';

        $('#' + parent).append(html);

        new Resizable($('#' + id));
        new DragObject($('#' + id)[0]);

        $('#' + id).css({
          position: 'absolute',
          width: width,
          height: height
        }).offset({
          left: Math.round($('#' + parent).offset().left) + offsetX,
          top: Math.round($('#' + parent).offset().top) + offsetY
        });

        break;

    case 'toggle':
      label = find(id, nodeTree['screen']['children'])['label'];
      html = '<div class="resizable" id="' + id + '"> \
                        <label data-role="toggle" class="_Gp_element ionic toggle toggle-balanced" id="' + (Number(id) - 1) + '"> \
                            <input type="checkbox"> \
                            <div class="ionic track"> \
                                <div class="ionic handle"></div> \
                            </div> \
                        </label> \
                    </div>';

      $('#' + parent).append(html);

      new Resizable($('#' + id));
      new DragObject($('#' + id)[0]);

      $('#' + id).css({
        position: 'absolute',
        width: width,
        height: height
      }).offset({
        left: Math.round($('#' + parent).offset().left) + offsetX,
        top: Math.round($('#' + parent).offset().top) + offsetY
      });

      break;
    case 'checkbox':
      label = find(id, nodeTree['screen']['children'])['label'];
      html = '<div class="resizable" id="' + id + '"> \
			            <div data-role="checkbox" class="_Gp_element ionic item item-checkbox" id="' + (Number(id) - 1) + '"> \
			                <label class="ionic checkbox"> \
			                    <input type="checkbox"> \
			                    <span>' + label + '</span> \
			                </label> \
			            </div> \
			        </div>';

      $('#' + parent).append(html);

      new Resizable($('#' + id));
      new DragObject($('#' + id)[0]);

      $('#' + id).css({
        position: 'absolute',
        width: width,
        height: height
      }).offset({
        left: Math.round($('#' + parent).offset().left) + offsetX,
        top: Math.round($('#' + parent).offset().top) + offsetY
      });

      break;
    case 'radio':
      html = '<div class="resizable" id="' + id + '"> \
   			            <div data-role="radio" class="_Gp_element ionic list" id="' + (Number(id) - 1) + '">';
      html += '<label class="ionic item item-radio"> \
   			                <input type="radio" name="group" checked> \
   			                <div class="ionic item-content">' + columns[0]['label'] + '</div> \
   			                <i class="ionic radio-icon ion-ios-circle-filled"></i> \
   			             </label>';
      for (var k = 1; k < columns.length; k++) {
        html += '<label class="ionic item item-radio"> \
   					                    <input type="radio" name="group"> \
   					                    <div class="ionic item-content">' + columns[k]['label'] + '</div> \
   					                    <i class="ionic radio-icon ion-ios-circle-filled"></i> \
   					                 </label>';
      }
      html += '</div> \
   	        		</div>';

      $('#' + parent).append(html);

      new Resizable($('#' + id));
      new DragObject($('#' + id)[0]);

      $('#' + id).css({
        position: 'absolute',
        width: width,
        height: height
      }).offset({
        left: Math.round($('#' + parent).offset().left) + offsetX,
        top: Math.round($('#' + parent).offset().top) + offsetY
      });

      break;
    case 'range':
      html = '<div class="resizable" id="' + id + '"> \
                        <div data-role="range" class="_Gp_element ionic item range" id="' + (Number(id) - 1) + '"> \
                            <input type="range" name="volume"> \
                        </div> \
                    </div>';

      $('#' + parent).append(html);

      new Resizable($('#' + id));
      new DragObject($('#' + id)[0]);

      $('#' + id).css({
        position: 'absolute',
        width: width,
        height: height
      }).offset({
        left: Math.round($('#' + parent).offset().left) + offsetX,
        top: Math.round($('#' + parent).offset().top) + offsetY
      });

      break;
    case 'input':
      html = '<div class="resizable" id="' + id + '"> \
			            <label data-role="input" class="_Gp_element ionic item item-input" id="' + (Number(id) - 1) + '"> \
			                <input type="text" placeholder="Some text here..."> \
			            </label> \
			        </div>';
      $('#' + parent).append(html);

      new Resizable($('#' + id));
      new DragObject($('#' + id)[0]);

      $('#' + id).css({
        position: 'absolute',
        width: width,
        height: height
      }).offset({
        left: Math.round($('#' + parent).offset().left) + offsetX,
        top: Math.round($('#' + parent).offset().top) + offsetY
      });

      break;
    /** Rashmi **/
    case 'date':
      html = '<div class="resizable" id="' + id + '"> \
						<date-picker id="' + (~~id - 1) + '"> \
					</div>';
      $('#' + parent).append($compile(html)($scope));

      new Resizable($('#' + id));
      new DragObject($('#' + id)[0]);

      $('#' + id).css({
        position: 'absolute',
        width: width,
        height: height
      }).offset({
        left: Math.round($('#' + parent).offset().left) + offsetX,
        top: Math.round($('#' + parent).offset().top) + offsetY
      });

      break;
    case 'header':
      label = find(id, nodeTree['screen']['children'])['label'];
      html = '<div class="resizable" id="' + id + '"> \
                        <div data-role="header" class="_Gp_element ionic bar bar-header bar-light" id="' + (Number(id) - 1) + '"> \
                            <h1 class="title">' + label + '</h1> \
                        </div> \
                    </div>';
      $('#' + parent).append(html);

      new Resizable($('#' + id));
      new DragObject($('#' + id)[0]);

      $('#' + id).css({
        position: 'absolute',
        width: width,
        height: height
      }).offset({
        left: Math.round($('#' + parent).offset().left) + offsetX,
        top: Math.round($('#' + parent).offset().top) + offsetY
      });

      break;
    case 'footer':
      label = find(id, nodeTree['screen']['children'])['label'];
      html = '<div class="resizable" id="' + id + '"> \
                        <div data-role="footer" class="_Gp_element ionic bar bar-footer bar-light" id="' + (Number(id) - 1) + '"> \
                            <p class="title">' + label + '</p> \
                        </div> \
                    </div>';
      $('#' + parent).append(html);

      new Resizable($('#' + id));
      new DragObject($('#' + id)[0]);

      $('#' + id).css({
        position: 'absolute',
        width: width,
        height: height
      }).offset({
        left: Math.round($('#' + parent).offset().left) + offsetX,
        top: Math.round($('#' + parent).offset().top) + offsetY
      });

      break;
    case 'list':
      html = '<div class="resizable" id="' + id + '"> \
                        <ul data-role="list" class="_Gp_element ionic list" id="' + (Number(id) - 1) + '">';
      for (var k = 0; k < columns.length; k++) {
        html += '<li class="ionic item">' + columns[k]['label'] + '</li>';
      }
      html += '</ul> \
            		</div>';
      $('#' + parent).append(html);

      new Resizable($('#' + id));
      new DragObject($('#' + id)[0]);

      $('#' + id).css({
        position: 'absolute',
        width: width,
        height: height
      }).offset({
        left: Math.round($('#' + parent).offset().left) + offsetX,
        top: Math.round($('#' + parent).offset().top) + offsetY
      });

      break;
    /* Boopathi */
    case 'image':
      html = '<div class="resizable" id="' + id + '"> \
						<div data-role="image" class="_Gp_element image" id="' + (Number(id) - 1) + '">\
							<img src="' + find(id, nodeTree['screen']['children'])['image_src'] + '" class="image-default">\
						</div>\
					</div>';
      $('#' + parent).append(html);

      new Resizable($('#' + id));
      new DragObject($('#' + id)[0]);

      $('#' + id).css({
        position: 'absolute',
        width: width,
        height: height
      }).offset({
        left: Math.round($('#' + parent).offset().left) + offsetX,
        top: Math.round($('#' + parent).offset().top) + offsetY
      });

      break;
      
    case 'map':
		console.log("id----------MAP-------234------AXE--------------->",id);
		//location = find(id, nodeTree['screen']['children'])['map-input'];
		html = '<div class="resizable" id="' + id + '"> \
		<div data-role="map" class="_Gp_element map" id="' + (Number(id) - 1) + '">\
		<img src="app/img/icons/ionic/icon-map.svg" class="map-image-default">\
		</div>\
		</div>';
		$('#' + parent).append(html);

		new Resizable($('#' + id));
		new DragObject($('#' + id)[0]);

		$('#' + id).css({
			position: 'absolute',
			width: width,
			height: height
		}).offset({
			left: Math.round($('#' + parent).offset().left) + offsetX,
			top: Math.round($('#' + parent).offset().top) + offsetY
		});

		break;
		
  }
}

/* update desktop complex components */
function showMobileScreen($scope, $compile, json) {
  var firstLevel, secondLevel, thirdLevel;
  var html, id, parent, type, width, height,
    offsetX, offsetY, parentOffsetX, parentOffsetY,
    rows, columns, panels, label;

  /* show elements in Axe */
  var children = json['children'] ? json['children'] : {};
  nodeTree['screen']['children'] = children ? children : {};

  for (firstLevel in children) {
    if (children.hasOwnProperty(firstLevel)) {
      id = firstLevel;
      parent = 'screen';
      type = children[firstLevel]['type'];
      width = children[firstLevel][window.orientation + '_width'];
      height = children[firstLevel][window.orientation + '_height'];
      offsetX = children[firstLevel][window.orientation + 'X'];
      offsetY = children[firstLevel][window.orientation + 'Y'];
      rows = children[firstLevel]['rows'];
      columns = children[firstLevel]['columns'];
      showMobileLevel($scope, $compile, id, parent, type, width, height, offsetX, offsetY, rows, columns);
      switch (type) {
        case 'card':
          /* Boopathi */
          html = '<div class="resizable" id="' + Number(id) + '"> \
			                    <div data-role="card" class="_Gp_element ionic card" id="' + (Number(id) - 1) + '"> \
			                        <div data-role="card-header" class="ionic item item-divider">' + nodeTree['screen']['children'][id]['header'] + '</div> \
			                        <div data-role="card-content" class="ionic item-text-wrap" id="' + (Number(id) - 2) + '"></div> \
			                        <div data-role="card-footer" class="ionic item item-divider">' + nodeTree['screen']['children'][id]['footer'] + '</div> \
			                    </div> \
			                </div>';

          $('#' + parent).append(html);

          new Resizable($('#' + id));
          new DragObject($('#' + id)[0]);
          new DropTarget($('#' + id).find('.item-text-wrap')[0]);

          $('#' + id).css({
            position: 'absolute',
            width: width,
            height: height
          }).offset({
            left: Math.round($('#' + parent).offset().left) + offsetX,
            top: Math.round($('#' + parent).offset().top) + offsetY
          });

          /* nested elements */
          if (!$.isEmptyObject(nodeTree['screen']['children'][firstLevel]['children'])) {
            for (secondLevel in nodeTree['screen']['children'][firstLevel]['children']) {
              if (nodeTree['screen']['children'][firstLevel]['children'].hasOwnProperty(secondLevel)) {
                id = secondLevel;
                type = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['type'];
                width = nodeTree['screen']['children'][firstLevel]['children'][secondLevel][window.orientation + '_width'];
                height = nodeTree['screen']['children'][firstLevel]['children'][secondLevel][window.orientation + '_height'];
                parentOffsetX = nodeTree['screen']['children'][firstLevel]['children'][secondLevel][window.orientation + 'OffsetX'] + children[firstLevel][window.orientation + 'X'];
                parentOffsetY = nodeTree['screen']['children'][firstLevel]['children'][secondLevel][window.orientation + 'OffsetY'] + children[firstLevel][window.orientation + 'Y'];
                rows = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['rows'];
                columns = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['columns'];

                showMobileLevel($scope, $compile, id, parent, type, width, height, parentOffsetX, parentOffsetY, rows, columns);
              }
            }
          }
          break;
        case 'tab':
          panels = Object.keys(nodeTree['screen']['children'][id]['children']);
          html = '<div class="resizable" id="' + id + '"> \
				                    <div data-role="tab" class="_Gp_element ionic tab" id="' + (Number(id) - (panels.length + 1)) + '"> \
				                        <div data-role="tab-content" class="ionic tab-content"> \
				                            <div data-role="section" class="ionic section active" id="' + panels[0] + '"></div>';
          for (var i = 1; i < panels.length; i++) {
            html += '<div data-role="section" class="ionic section" id="' + panels[i] + '"></div>';
          }
          html += '</div> \
				                        <ul data-role="tab-list" class="ionic tab-list"> \
				                            <li class="active"> \
				                                <a data-ng-href="#' + panels[0] + '">Tab 1</a> \
				                            </li>';
          for (var i = 1; i < panels.length; i++) {
            html += '<li><a data-ng-href="#' + panels[i] + '">' + find(panels[i], nodeTree['screen']['children'][id]['children'])['label'] + '</a></li>';
          }
          html += '</ul> \
				                    </div> \
				                </div>';

          $('#' + parent).append(html);

          new Resizable($('#' + id));
          new DragObject($('#' + id)[0]);
          $('#' + id).find('.section').each(function (index, panel) {
            new DropTarget(panel);
          });

          $('#' + id).css({
            position: 'absolute',
            width: width,
            height: height
          }).offset({
            left: Math.round($('#' + parent).offset().left) + offsetX,
            top: Math.round($('#' + parent).offset().top) + offsetY
          });

          for (secondLevel in nodeTree['screen']['children'][firstLevel]['children']) {
            if (nodeTree['screen']['children'][firstLevel]['children'].hasOwnProperty(secondLevel)) {

              /* nested elements */
              if (!$.isEmptyObject(nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'])) {
                for (thirdLevel in nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children']) {
                  if (nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'].hasOwnProperty(thirdLevel)) {
                    id = thirdLevel;
                    type = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel]['type'];
                    width = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel][window.orientation + '_width'];
                    height = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel][window.orientation + '_height'];
                    parentOffsetX = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel][window.orientation + 'OffsetX'] + children[firstLevel][window.orientation + 'X'];
                    parentOffsetY = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel][window.orientation + 'OffsetY'] + children[firstLevel][window.orientation + 'Y'];
                    rows = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel]['rows'];
                    columns = nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel]['columns'];

                    showMobileLevel($scope, $compile, id, parent, type, width, height, parentOffsetX, parentOffsetY, rows, columns);
                  }
                }
              }
            }
          }

          /* show children of current tab panel and hide siblings children */
          var tab = $('#' + panels[0]).parents('.resizable').attr('id');

          if (nodeTree['screen']['children'][tab].hasOwnProperty('children')) {
            for (firstLevel in nodeTree['screen']['children'][tab]['children']) {
              if (nodeTree['screen']['children'][tab]['children'].hasOwnProperty(firstLevel)) {
                if (nodeTree['screen']['children'][tab]['children'][firstLevel].hasOwnProperty('children')) {
                  if (firstLevel === panels[0]) {
                    for (secondLevel in nodeTree['screen']['children'][tab]['children'][firstLevel]['children']) {
                      if (nodeTree['screen']['children'][tab]['children'][firstLevel]['children'].hasOwnProperty(secondLevel)) {
                        $('#' + secondLevel).show();
                      }
                    }
                  } else {
                    for (secondLevel in nodeTree['screen']['children'][tab]['children'][firstLevel]['children']) {
                      if (nodeTree['screen']['children'][tab]['children'][firstLevel]['children'].hasOwnProperty(secondLevel)) {
                        $('#' + secondLevel).hide();
                      }
                    }
                  }
                }
              }
            }
          }

          break;
      }
    }
  }
}

function DeleteComponent($scope) {
  var firstLevel, secondLevel, thirdLevel;
  var $selected = $('#screen').find('.selected');

  if ($selected.length) {
    /* delete related components from DOM */
    switch ($scope.element_type) {
      case 'border-container':
      case 'panel':
        for (firstLevel in nodeTree['screen']['children']) {
          if (nodeTree['screen']['children'].hasOwnProperty(firstLevel)) {
            if (firstLevel == $scope.element_id) {
              for (secondLevel in nodeTree['screen']['children'][firstLevel]['children']) {
                if (nodeTree['screen']['children'][firstLevel]['children'].hasOwnProperty(secondLevel)) {
                  /* add nested components to delete_widgets array */
                  if (find(secondLevel, nodeTree['screen']['children'])['id']) {
                    nodeTree['screen']['deleted_widgets'].push(find(secondLevel, nodeTree['screen']['children'])['id']);
                  }
                  /* delete nested components */
                  $('#screen').find('#' + secondLevel).remove();
                }
              }
            }
          }
        }

        break;
      case 'tab':
      case 'accordion':
        for (firstLevel in nodeTree['screen']['children']) {
          if (nodeTree['screen']['children'].hasOwnProperty(firstLevel)) {
            if (firstLevel == $scope.element_id) {
              for (secondLevel in nodeTree['screen']['children'][firstLevel]['children']) {
                if (nodeTree['screen']['children'][firstLevel]['children'].hasOwnProperty(secondLevel)) {
                  /* add sections to delete_widgets array */
                  if (find(secondLevel, nodeTree['screen']['children'])['id']) {
                    nodeTree['screen']['deleted_widgets'].push(find(secondLevel, nodeTree['screen']['children'])['id']);
                  }

                  for (thirdLevel in nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children']) {
                    if (nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'].hasOwnProperty(thirdLevel)) {
                      /* add nested components to delete_widgets array */
                      if (find(thirdLevel, nodeTree['screen']['children'])['id']) {
                        nodeTree['screen']['deleted_widgets'].push(find(thirdLevel, nodeTree['screen']['children'])['id']);
                      }
                      /* delete nested components */
                      $('#screen').find('#' + thirdLevel).remove();
                    }
                  }
                }
              }
            }
          }
        }

        break;
    }

    /* add component to delete_widgets array */
    if (find($selected.attr('id'), nodeTree['screen']['children'])['id']) {
      nodeTree['screen']['deleted_widgets'].push(find($selected.attr('id'), nodeTree['screen']['children'])['id']);
    }

    /* delete component from nodeTree screen children */
    for (firstLevel in nodeTree['screen']['children']) {
      if (nodeTree['screen']['children'].hasOwnProperty(firstLevel)) {
        if (firstLevel == $selected.attr('id')) {
          delete nodeTree['screen']['children'][firstLevel];
        } else {
          for (secondLevel in nodeTree['screen']['children'][firstLevel]['children']) {
            if (nodeTree['screen']['children'][firstLevel]['children'].hasOwnProperty(secondLevel)) {
              if (secondLevel == $selected.attr('id')) {
                delete nodeTree['screen']['children'][firstLevel]['children'][secondLevel];
              } else {
                for (thirdLevel in nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children']) {
                  if (nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'].hasOwnProperty(thirdLevel)) {
                    if (thirdLevel == $selected.attr('id')) {
                      delete nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel];
                    }
                  }
                }
              }
            }
          }
        }
      }
    }

    /* remove Date picker from DOM */
    $('.datepicker').remove();

    /* remove component from DOM */
    $selected.remove();

    /* hide delete button */
    $('.delete').removeClass('visible');

    /* hide customize table toolbar */
    $('#grid-panel').addClass('hidden');

    /* Boopathi */
    /* hide customize tab panel */
    $('#tab-panel').addClass('hidden');

    /* hide customize accordion panel */
    $('#accordion-panel').addClass('hidden');
    
    /* hide customize button group panel */
    $('#checkbox-panel').addClass('hidden');
    
    $('#radio-panel').addClass('hidden');

    /* clear element info from Components, Data Bindings and Verb Bindings tabs */
    $scope.clearElementInfo();

    console.log(nodeTree['screen']);
  }
}

/* TODO: tab panel elements position calculation bug */
function changeOrientation(value) {
  var firstLevel, secondLevel, thirdLevel;
  window.orientation = value;

  /* set orientation */
  nodeTree['screen']['orientation'] = window.orientation;

  /* add orientation class */
  $('.device').removeClass('portrait landscape').addClass(window.orientation);

  for (firstLevel in nodeTree['screen']['children']) {
    if (nodeTree['screen']['children'].hasOwnProperty(firstLevel)) {
      $('#' + firstLevel).css({
        width: nodeTree['screen']['children'][firstLevel][window.orientation + '_width'] + 'px',
        height: nodeTree['screen']['children'][firstLevel][window.orientation + '_height'] + 'px',
        left: nodeTree['screen']['children'][firstLevel][window.orientation + 'X'] + $('#screen').offset().left + 'px',
        top: nodeTree['screen']['children'][firstLevel][window.orientation + 'Y'] + $('#screen').offset().top + 'px'
      });

      if (nodeTree['screen']['children'][firstLevel].hasOwnProperty('children')) {
        for (secondLevel in nodeTree['screen']['children'][firstLevel]['children']) {
          if (nodeTree['screen']['children'][firstLevel]['children'].hasOwnProperty(secondLevel)) {
            if (nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['type'].indexOf('section') === -1) {
              $('#' + secondLevel).css({
                width: nodeTree['screen']['children'][firstLevel]['children'][secondLevel][window.orientation + '_width'] + 'px',
                height: nodeTree['screen']['children'][firstLevel]['children'][secondLevel][window.orientation + '_height'] + 'px',
                left: nodeTree['screen']['children'][firstLevel]['children'][secondLevel][window.orientation + 'OffsetX'] + parseInt($('#' + firstLevel).css('left'), 10) + 'px',
                top: nodeTree['screen']['children'][firstLevel]['children'][secondLevel][window.orientation + 'OffsetY'] + parseInt($('#' + firstLevel).css('top'), 10) + 'px'
              });
            } else {
              if (nodeTree['screen']['children'][firstLevel]['children'][secondLevel].hasOwnProperty('children')) {
                for (thirdLevel in nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children']) {
                  if (nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'].hasOwnProperty(thirdLevel)) {
                    $('#' + thirdLevel).css({
                      width: nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel][window.orientation + '_width'] + 'px',
                      height: nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel][window.orientation + '_height'] + 'px',
                      left: nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel][window.orientation + 'OffsetX'] + parseInt($('#' + firstLevel).css('left'), 10) + 'px',
                      top: nodeTree['screen']['children'][firstLevel]['children'][secondLevel]['children'][thirdLevel][window.orientation + 'OffsetY'] + parseInt($('#' + firstLevel).css('top'), 10) + 'px'
                    });
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}

function openDialog($modal, msg) {
  var modalInstance = $modal.open({
    animation: true,
    size: 'sm',
    backdrop: 'static',
    keyboard: false,
    templateUrl: 'app/views/en-US/templates/modals/axe/validation-message.html',
    controller: 'ModalCtrl',
    resolve: {
      data: function () {
        return angular.copy(msg); // deep copy
      }
    }
  });
  return modalInstance;
}
