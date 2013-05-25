import net.sourceforge.htmlunit.corejs.javascript.JavaScriptException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class SeleniumStart
{
	private WebDriver driver;

	public SeleniumStart()
	{
	  driver = new ChromeDriver();
	}
	
	public void showPageSourceAndWait()
	{
		driver.get( "https://people.ok.ubc.ca/bowenhui/iui/");
		String page = driver.getPageSource();
		System.out.println( page );
		try 
		{
			Thread.currentThread().sleep( 1000 );
		} 
		catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void changePageAndWait()
	{
		System.out.println( "switch to another page" );
		driver.get( "https://people.ok.ubc.ca/bowenhui/231/" );
		try 
		{
			Thread.currentThread().sleep( 1000 );
		} 
		catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void cleanup()
	{
  	    driver.close();
  	    driver.quit();
	}

	public static void main(String[] args) 
	{
		SeleniumStart ex = new SeleniumStart();
		ex.showPageSourceAndWait();
		ex.changePageAndWait();
		ex.openTab( "http://www.google.ca" );
		ex.cleanup();
		System.out.println( "end of main" );
	}
	/**
	 * Executes a script on an element
	 * @note Really should only be used when the web driver is sucking at exposing
	 * functionality natively
	 * @param script The script to execute
	 * @param element The target of the script, referenced as arguments[0]
	 */
	public void trigger(String script, WebElement element) 
	{
	    ((JavascriptExecutor)driver).executeScript(script, element);
	}

	/** Executes a script
	 * @note Really should only be used when the web driver is sucking at exposing
	 * functionality natively
	 * @param script The script to execute
	 */
	public Object trigger(String script) 
	{
	    return ((JavascriptExecutor)driver).executeScript(script);
	}

	/**
	 * Opens a new tab for the given URL
	 * @param url The URL to 
	 * @throws JavaScriptException If unable to open tab
	 */
	public void openTab(String url) 
	{
	    String script = "var d=document,a=d.createElement('a');a.target='_blank';a.href='%s';a.innerHTML='.';d.body.appendChild(a);return a";
	    Object element = trigger(String.format(script, url));
	    if (element instanceof WebElement) {
	        WebElement anchor = (WebElement) element; anchor.click();
	        trigger("var a=arguments[0];a.parentNode.removeChild(a);", anchor);
	    } else {
	        throw new JavaScriptException(element, "Unable to open tab", 1);
	    }       
	}
}
