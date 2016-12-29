import { DashboardPortalPage } from './app.po';

describe('dashboard-portal App', function() {
  let page: DashboardPortalPage;

  beforeEach(() => {
    page = new DashboardPortalPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
