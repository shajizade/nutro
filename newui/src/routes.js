import React from "react";

const Toaster = React.lazy(() =>
  ('./views/notifications/toaster/Toaster'));
const Tables = React.lazy(() =>
  ('./views/base/tables/Tables'));

const Breadcrumbs = React.lazy(() =>
  ('./views/base/breadcrumbs/Breadcrumbs'));
const Cards = React.lazy(() =>
  ('./views/base/cards/Cards'));
const Carousels = React.lazy(() =>
  ('./views/base/carousels/Carousels'));
const Collapses = React.lazy(() =>
  ('./views/base/collapses/Collapses'));
const BasicForms = React.lazy(() =>
  ('./views/base/forms/BasicForms'));

const Jumbotrons = React.lazy(() =>
  ('./views/base/jumbotrons/Jumbotrons'));
const ListGroups = React.lazy(() =>
  ('./views/base/list-groups/ListGroups'));
const Navbars = React.lazy(() =>
  ('./views/base/navbars/Navbars'));
const Navs = React.lazy(() =>
  ('./views/base/navs/Navs'));
const Paginations = React.lazy(() =>
  ('./views/base/paginations/Pagnations'));
const Popovers = React.lazy(() =>
  ('./views/base/popovers/Popovers'));
const ProgressBar = React.lazy(() =>
  ('./views/base/progress-bar/ProgressBar'));
const Switches = React.lazy(() =>
  ('./views/base/switches/Switches'));

const Tabs = React.lazy(() =>
  ('./views/base/tabs/Tabs'));
const Tooltips = React.lazy(() =>
  ('./views/base/tooltips/Tooltips'));
const BrandButtons = React.lazy(() =>
  ('./views/buttons/brand-buttons/BrandButtons'));
const ButtonDropdowns = React.lazy(() =>
  ('./views/buttons/button-dropdowns/ButtonDropdowns'));
const ButtonGroups = React.lazy(() =>
  ('./views/buttons/button-groups/ButtonGroups'));
const Buttons = React.lazy(() =>
  ('./views/buttons/buttons/Buttons'));
const Charts = React.lazy(() =>
  ('./views/charts/Charts'));
const Dashboard = React.lazy(() =>
  ('./views/dashboard/Dashboard'));
const CoreUIIcons = React.lazy(() =>
  ('./views/icons/coreui-icons/CoreUIIcons'));
const Flags = React.lazy(() =>
  ('./views/icons/flags/Flags'));
const Brands = React.lazy(() =>
  ('./views/icons/brands/Brands'));
const Alerts = React.lazy(() =>
  ('./views/notifications/alerts/Alerts'));
const Badges = React.lazy(() =>
  ('./views/notifications/badges/Badges'));
const Modals = React.lazy(() =>
  ('./views/notifications/modals/Modals'));
const Colors = React.lazy(() =>
  ('./views/theme/colors/Colors'));
const Typography = React.lazy(() =>
  ('./views/theme/typography/Typography'));
const Widgets = React.lazy(() =>
  ('./views/widgets/Widgets'));
const Users = React.lazy(() =>
  ('./views/users/Users'));
const User = React.lazy(() =>
  ('./views/users/User'));

const FoodCalculate = React.lazy(() =>
  ('./views/food/FoodCalculate'));
const UpdateRecipe = React.lazy(() =>
  ('./views/admin/UpdateRecipe'));
const ResearchList = React.lazy(() =>
  ('./views/research/ResearchList'));
const CaseList = React.lazy(() =>
  ('./views/research/CaseList'));
const CaseDetail = React.lazy(() =>
  ('./views/research/CaseDetail'));
const FoodList = React.lazy(() =>
  ('./views/admin/FoodList'));


const routes = [
  {path: '/', exact: true, name: 'Home'},
  {path: '/dashboard', name: 'Dashboard', component: Dashboard},
  {path: '/theme', name: 'Theme', component: Colors, exact: true},
  {path: '/theme/colors', name: 'Colors', component: Colors},
  {path: '/theme/typography', name: 'Typography', component: Typography},
  {path: '/base', name: 'Base', component: Cards, exact: true},
  {path: '/base/breadcrumbs', name: 'Breadcrumbs', component: Breadcrumbs},
  {path: '/base/cards', name: 'Cards', component: Cards},
  {path: '/base/carousels', name: 'Carousel', component: Carousels},
  {path: '/base/collapses', name: 'Collapse', component: Collapses},
  {path: '/base/forms', name: 'Forms', component: BasicForms},
  {path: '/base/jumbotrons', name: 'Jumbotrons', component: Jumbotrons},
  {path: '/base/list-groups', name: 'List Groups', component: ListGroups},
  {path: '/base/navbars', name: 'Navbars', component: Navbars},
  {path: '/base/navs', name: 'Navs', component: Navs},
  {path: '/base/paginations', name: 'Paginations', component: Paginations},
  {path: '/base/popovers', name: 'Popovers', component: Popovers},
  {path: '/base/progress-bar', name: 'Progress Bar', component: ProgressBar},
  {path: '/base/switches', name: 'Switches', component: Switches},
  {path: '/base/tables', name: 'Tables', component: Tables},
  {path: '/base/tabs', name: 'Tabs', component: Tabs},
  {path: '/base/tooltips', name: 'Tooltips', component: Tooltips},
  {path: '/buttons', name: 'Buttons', component: Buttons, exact: true},
  {path: '/buttons/buttons', name: 'Buttons', component: Buttons},
  {path: '/buttons/button-dropdowns', name: 'Dropdowns', component: ButtonDropdowns},
  {path: '/buttons/button-groups', name: 'Button Groups', component: ButtonGroups},
  {path: '/buttons/brand-buttons', name: 'Brand Buttons', component: BrandButtons},
  {path: '/charts', name: 'Charts', component: Charts},
  {path: '/icons', exact: true, name: 'Icons', component: CoreUIIcons},
  {path: '/icons/coreui-icons', name: 'CoreUI Icons', component: CoreUIIcons},
  {path: '/icons/flags', name: 'Flags', component: Flags},
  {path: '/icons/brands', name: 'Brands', component: Brands},
  {path: '/notifications', name: 'Notifications', component: Alerts, exact: true},
  {path: '/notifications/alerts', name: 'Alerts', component: Alerts},
  {path: '/notifications/badges', name: 'Badges', component: Badges},
  {path: '/notifications/modals', name: 'Modals', component: Modals},
  {path: '/notifications/toaster', name: 'Toaster', component: Toaster},
  {path: '/widgets', name: 'Widgets', component: Widgets},
  {path: '/users', exact: true, name: 'Users', component: Users},
  {path: '/users/:id', exact: true, name: 'User Details', component: User},
  {path: '/food-calculate', exact: true, name: 'محاسبه', component: FoodCalculate},
  {path: '/admin/updateRecipe/:foodId', exact: true, name: 'ویرایش دستور', component: UpdateRecipe},
  {path: '/research/list', exact: true, name: 'لیست تحقیقات', component: ResearchList, isPublic: true},
  {path: '/admin/foods', exact: true, name: 'لیست غذاها', component: FoodList, isPublic: false},
  {path: '/research/:researchId/case', exact: true, name: 'لیست موارد', component: CaseList, isPublic: false},
  {
    path: '/research/:researchId/case/:caseId/detail',
    exact: true,
    name: 'جزئیات مورد',
    component: CaseDetail,
    isPublic: false
  }
];

export default routes;
