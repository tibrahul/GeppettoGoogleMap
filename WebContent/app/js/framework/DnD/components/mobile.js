function MobileComponents(element_id) {
  var node, element, isMenuItem, menuItemId;

  switch (element_id) {
    case 'icon-label':
      isMenuItem = true;
      menuItemId = 'icon-label';
      node = '<div class="resizable" id="' + (id + 1) + '"> \
                  <label data-role="label" class="_Gp_element" id="' + id + '">Label</label> \
               </div>';
      $(node).appendTo('.axe');
      ++id;
      element = $('#' + id)[0];

      break;
    case 'icon-button':
      isMenuItem = true;
      menuItemId = 'icon-button';
      node = '<div class="resizable" id="' + (id + 1) + '"> \
                  <button type="button" data-role="button" class="_Gp_element ionic button button-light" id="' + id + '">Button</button> \
              </div>';
      $(node).appendTo('.axe');
      ++id;
      element = $('#' + id)[0];

      break;
    case 'icon-camera':
      isMenuItem = true;
      menuItemId = 'icon-camera';
      node = '<div class="resizable" id="' + (id + 1) + '"> \
                <button type="button" data-role="camera" class="_Gp_element ionic button button-light" id="' + id + '"> \
                  <i class="fa fa-camera"></i> \
                  <span>Take Photo</span> \
                </button> \
              </div>';
      $(node).appendTo('.axe');
      id++;
      element = $('#' + id)[0];

      break;
    
    case 'icon-video':
        isMenuItem = true;
        menuItemId = 'icon-video';
        node = '<div class="resizable" id="' + (id + 1) + '"> \
          	<img src="app/img/icons/ionic/video.png" data-role="video" class="image-default" id="' + id + '">\
          </div>';
        $(node).appendTo('.axe');
        id++;
        element = $('#' + id)[0];

        break; 
    case 'icon-recorder':
        isMenuItem = true;
        menuItemId = 'icon-recorder';
        node = '<div class="resizable" id="' + (id + 1) + '"> \
          	<img src="app/img/icons/ionic/record.png" data-role="recorder" class="image-default" id="' + id + '">\
          </div>';
        $(node).appendTo('.axe');
        id++;
        element = $('#' + id)[0];

        break;  
    case 'icon-toggle':
      isMenuItem = true;
      menuItemId = 'icon-toggle';
      node = '<div class="resizable" id="' + (id + 1) + '"> \
                  <label data-role="toggle" class="_Gp_element ionic toggle toggle-balanced" id="' + id + '"> \
                      <input type="checkbox"> \
                      <div class="ionic track"> \
                          <div class="ionic handle"></div> \
                      </div> \
                  </label> \
              </div>';
      $(node).appendTo('.axe');
      ++id;
      element = $('#' + id)[0];

      break;
    case 'icon-checkbox':
      isMenuItem = true;
      menuItemId = 'icon-checkbox';
      node = '<div class="resizable" id="' + (id + 1) + '"> \
                  <div data-role="checkbox" class="_Gp_element ionic item item-checkbox" id="' + id + '"> \
                      <label class="ionic checkbox"> \
                          <input type="checkbox"> \
                          <span>Checkbox Label</span> \
                      </label> \
                  </div> \
              </div>';
      $(node).appendTo('.axe');
      ++id;
      element = $('#' + id)[0];

      break;
    case 'icon-radio':
      isMenuItem = true;
      menuItemId = 'icon-radio';
      node = '<div class="resizable" id="' + (id + 1) + '"> \
                  <div data-role="radio" class="_Gp_element ionic list" id="' + id + '"> \
                      <label class="ionic item item-radio"> \
                          <input type="radio" name="group" checked> \
                          <div class="ionic item-content">First</div> \
                          <i class="ionic radio-icon ion-ios-circle-filled"></i> \
                      </label> \
                      <label class="ionic item item-radio"> \
                          <input type="radio" name="group"> \
                          <div class="ionic item-content">Second</div> \
                          <i class="ionic radio-icon ion-ios-circle-filled"></i> \
                      </label> \
                  </div> \
              </div>';
      $(node).appendTo('.axe');
      ++id;
      element = $('#' + id)[0];

      break;
    case 'icon-range':
      isMenuItem = true;
      menuItemId = 'icon-range';
      node = '<div class="resizable" id="' + (id + 1) + '"> \
                  <div data-role="range" class="_Gp_element ionic item range" id="' + id + '"> \
                      <input type="range" name="volume"> \
                  </div> \
              </div>';
      $(node).appendTo('.axe');
      ++id;
      element = $('#' + id)[0];

      break;
    /* added by Rashmi */
    case 'icon-date':
      isMenuItem = true;
      menuItemId = 'icon-date';
      node = '<div class="resizable" id="' + (id + 1) + '"> \
                  <div data-role="date" class="_Gp_element date input-group" id="' + id + '"> \
                      <input type="text" class="form-control"> \
                      <span class="input-group-addon"><i class="fa fa-calendar"></i></span> \
                  </div> \
              </div>';
      $(node).appendTo('.axe');
      $('#' + id).datepicker();
      ++id;
      element = $('#' + id)[0];

      break;
    case 'icon-input':
      isMenuItem = true;
      menuItemId = 'icon-input';
      node = '<div class="resizable" id="' + (id + 1) + '"> \
                  <label data-role="input" class="_Gp_element ionic item item-input" id="' + id + '"> \
                      <input type="text" placeholder="Some text here..."> \
                  </label> \
              </div>';
      $(node).appendTo('.axe');
      ++id;
      element = $('#' + id)[0];

      break;
    case 'icon-header':
      isMenuItem = true;
      menuItemId = 'icon-header';
      node = '<div class="resizable" id="' + (id + 1) + '"> \
                  <div data-role="header" class="_Gp_element ionic bar bar-header bar-light" id="' + id + '"> \
                      <h1 class="title">Header</h1> \
                  </div> \
              </div>';
      $(node).appendTo('.axe');
      ++id;
      element = $('#' + id)[0];

      break;
    case 'icon-footer':
      isMenuItem = true;
      menuItemId = 'icon-footer';
      node = '<div class="resizable" id="' + (id + 1) + '"> \
                  <div data-role="footer" class="_Gp_element ionic bar bar-footer bar-light" id="' + id + '"> \
                      <p class="title">Footer</p> \
                  </div> \
              </div>';
      $(node).appendTo('.axe');
      ++id;
      element = $('#' + id)[0];

      break;
    case 'icon-list':
      isMenuItem = true;
      menuItemId = 'icon-list';
      node = '<div class="resizable" id="' + (id + 1) + '"> \
                  <ul data-role="list" class="_Gp_element ionic list" id="' + id + '"> \
                      <li class="ionic item">Item 1</li> \
                      <li class="ionic item">Item 2</li> \
                      <li class="ionic item">Item 3</li> \
                  </ul> \
              </div>';
      $(node).appendTo('.axe');
      ++id;
      element = $('#' + id)[0];

      break;
    case 'icon-card':
      isMenuItem = true;
      menuItemId = 'icon-card';
      node = '<div class="resizable" id="' + (id + 2) + '"> \
                  <div data-role="card" class="_Gp_element ionic card" id="' + id + '"> \
                      <div data-role="card-header" class="ionic item item-divider">Header</div> \
                      <div data-role="card-content" class="ionic item item-text-wrap" id="' + (id + 1) + '"></div> \
                      <div data-role="card-footer" class="ionic item item-divider">Footer</div> \
                  </div> \
              </div>';
      $(node).appendTo('.axe');
      id += 2;
      element = $('#' + id)[0];

      break;
    case 'icon-tab':
      isMenuItem = true;
      menuItemId = 'icon-tab';
      node = '<div class="resizable" id="' + (id + 4) + '"> \
                  <div data-role="tab" class="_Gp_element ionic tab" id="' + id + '"> \
                      <div data-role="tab-content" class="ionic tab-content"> \
                          <div data-role="section" class="ionic section active" id="' + (id + 1) + '"></div> \
                          <div data-role="section" class="ionic section" id="' + (id + 2) + '"></div> \
                          <div data-role="section" class="ionic section" id="' + (id + 3) + '"></div> \
                      </div> \
                      <ul data-role="tab-list" class="ionic tab-list"> \
                          <li class="active"> \
                              <a data-ng-href="#' + (id + 1) + '">Tab 1</a> \
                          </li> \
                          <li> \
                              <a data-ng-href="#' + (id + 2) + '">Tab 2</a> \
                          </li> \
                          <li> \
                              <a data-ng-href="#' + (id + 3) + '">Tab 3</a> \
                          </li> \
                      </ul> \
                  </div> \
              </div>';
      $(node).appendTo('.axe');
      id += 4;
      element = $('#' + id)[0];

      break;
    case 'icon-image':
      isMenuItem = true;
      menuItemId = 'icon-image';
      node = '<div class="resizable" id="' + (id + 1) + '"> \
                <div data-role="image" class="_Gp_element image" id="' + id + '">\
                  <img src="app/img/icons/icon-image-view.svg" class="image-default">\
                </div>\
              </div>';
      $(node).appendTo('.axe');
      ++id;
      element = $('#' + id)[0];

      break;
      
    case 'icon-select':
        isMenuItem = true;
        menuItemId = 'icon-select';
        node = '<div class="resizable" id="' + (id + 1) + '"> \
  								<select data-role="select" class="_Gp_element form-control" id="' + id + '"></select> \
  							</div>';
        $(node).appendTo('.axe');
        ++id;
        element = $('#' + id)[0];
        
        break;
        
    case 'icon-map':
    	console.log("id-----------------------------TEST------>>>>>>>>>",id);
    	//console.log("MAP LOACTION----------------->>>>>>>>>>>>>>>>.", $scope.map.location);
        isMenuItem = true;
        menuItemId = 'icon-map';
        node = '<div class="resizable" id="' + (id + 1) + '"> \
                  <div data-role="map" class="_Gp_element map" id="' + id + '">\
                    <img src="app/img/icons/ionic/icon-map.svg" class="map-default">\
                  </div>\
                </div>';
        $(node).appendTo('.axe');
        ++id;
        element = $('#' + id)[0];

        break;
    default:
      isMenuItem = false;
  }

  return {
    element: element,
    isMenuItem: isMenuItem,
    menuItemId: menuItemId
  };
}