package com.project.pin.system;

import org.junit.Test;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.junit.After;
import org.junit.Before;

public class TesteSistemaCadastroLogin {

    private WebDriver driver;

    // --- CONFIGURE SUAS URLs AQUI ---
    private final String URL_CADASTRO = "http://localhost:5173/cadastro"; // Substitua pela sua URL
    private final String URL_LOGIN = "http://localhost:5173/login";       // Substitua pela sua URL

    @Before
    public void setUp() {
        // Você precisa ter o chromedriver.exe no seu PATH
        // ou especificar o caminho com:
        // System.setProperty("webdriver.chrome.driver", "C:/caminho/para/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }


    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * ----------------------------------------
     * TESTES DE CADASTRO (RegisterForm.tsx)
     * ----------------------------------------
     */

    /**
     * CT01: Valida se o sistema efetua o cadastro de um usuário de forma correta.
     * Resultado Esperado: Redirecionamento para a tela de login.
     */
    @Test
    public void testCadastroComSucesso_CT01() throws InterruptedException {
        driver.get(URL_CADASTRO);

        // Preenche os campos (presumindo id == label em minúsculo)
        driver.findElement(By.id("username")).sendKeys("clarabecker");
        driver.findElement(By.id("senha")).sendKeys("minhaSenha123");
        driver.findElement(By.id("nome")).sendKeys("Clara Becker");
        driver.findElement(By.id("cpf")).sendKeys("12345678900");
        driver.findElement(By.id("endereco")).sendKeys("Rua das Flores, 120");
        driver.findElement(By.id("bairro")).sendKeys("Centro");
        driver.findElement(By.id("cidade")).sendKeys("Florianópolis");
        driver.findElement(By.id("cep")).sendKeys("88000000");
        driver.findElement(By.id("estado")).sendKeys("SC");
        driver.findElement(By.id("telefone")).sendKeys("48999998888");
        driver.findElement(By.id("email")).sendKeys("clara.becker@email.com");
        // driver.findElement(By.id("ID_DO_CAMPO_IMAGEM")).sendKeys("C:/caminho/perfil_clara.jpg");

        // Clica no botão "Salvar"
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Espera o processamento e redirecionamento
        Thread.sleep(2000);

        // Verifica se foi redirecionado para a URL de login
        String urlAtual = driver.getCurrentUrl();
        Assert.assertTrue("Deveria ter sido redirecionado para /login", urlAtual.contains("/login"));
    }

    /**
     * CT02: Valida se o sistema impede a criação de usuários com campos vazios.
     * Resultado Esperado: Mensagem de erro.
     * ATENÇÃO: O roteiro pedia "todos os campos devem ser preenchidos".
     * Seu código React gera "Erro ao cadastrar Usuario". O teste valida o código.
     */
    @Test
    public void testCadastroCamposVazios_CT02() throws InterruptedException {
        driver.get(URL_CADASTRO);

        // Deixa 'username' e 'nome' vazios, mas preenche outros (para forçar o erro)
        driver.findElement(By.id("username")).sendKeys(""); // Vazio
        driver.findElement(By.id("nome")).sendKeys("");     // Vazio
        driver.findElement(By.id("email")).sendKeys("clara.becker@email.com");
        driver.findElement(By.id("senha")).sendKeys("minhaSenha123");
        driver.findElement(By.id("cpf")).sendKeys("11122233300");
        // ... (pode ser necessário preencher todos os campos 'required' da API)

        // Clica no botão "Salvar"
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        Thread.sleep(1000);

        // Verifica a mensagem de erro genérica do 'catch'
        WebElement msgErro = driver.findElement(By.xpath("//*[contains(text(), 'Erro ao cadastrar Usuario')]"));
        Assert.assertEquals("Erro ao cadastrar Usuario", msgErro.getText());
    }

    /**
     * CT03: Verifica se o sistema impede o cadastro com um username ou cpf já cadastrados.
     * Resultado Esperado: Mensagem de erro.
     */
    @Test
    public void testCadastroDuplicado_CT03() throws InterruptedException {
        // Este teste assume que o usuário "clarabecker" / CPF "12345678900" JÁ EXISTE
        driver.get(URL_CADASTRO);

        // Preenche com os dados do CT01
        driver.findElement(By.id("username")).sendKeys("clarabecker");
        driver.findElement(By.id("senha")).sendKeys("minhaSenha123");
        driver.findElement(By.id("nome")).sendKeys("Clara Becker");
        driver.findElement(By.id("cpf")).sendKeys("12345678900");
        driver.findElement(By.id("endereco")).sendKeys("Rua das Flores, 120");
        driver.findElement(By.id("bairro")).sendKeys("Centro");
        driver.findElement(By.id("cidade")).sendKeys("Florianópolis");
        driver.findElement(By.id("cep")).sendKeys("88000000");
        driver.findElement(By.id("estado")).sendKeys("SC");
        driver.findElement(By.id("telefone")).sendKeys("48999998888");
        driver.findElement(By.id("email")).sendKeys("clara.becker@email.com");

        // Clica no botão "Salvar"
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        Thread.sleep(1000);

        // Verifica a mensagem de erro genérica do 'catch' do React
        WebElement msgErro = driver.findElement(By.xpath("//*[contains(text(), 'Erro ao cadastrar Usuario')]"));
        Assert.assertEquals("Erro ao cadastrar Usuario", msgErro.getText());
    }


    /**
     * ----------------------------------------
     * TESTES DE LOGIN (LoginForm.tsx)
     * ----------------------------------------
     */

    /**
     * CT04: Valida se o sistema efetua a autenticação de forma correta.
     * Resultado Esperado: Redirecionamento para /main.
     */
    @Test
    public void testLoginComSucesso_CT04() throws InterruptedException {
        // Este teste assume que o usuário "clarabecker" já foi cadastrado no CT01
        driver.get(URL_LOGIN);

        driver.findElement(By.id("username")).sendKeys("clarabecker");
        driver.findElement(By.id("senha")).sendKeys("minhaSenha123");

        // Clica no botão "Entrar" (submit)
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        Thread.sleep(2000);

        // Verifica se foi redirecionado para a página principal
        String urlAtual = driver.getCurrentUrl();
        Assert.assertTrue("Deveria ter sido redirecionado para /main", urlAtual.contains("/main"));
    }

    /**
     * CT05: Valida se o sistema impede a autenticação com senha incorreta.
     * Resultado Esperado: Mensagem "Usuário ou senha inválidos.".
     */
    @Test
    public void testLoginComSenhaIncorreta_CT05() throws InterruptedException {
        driver.get(URL_LOGIN);

        driver.findElement(By.id("username")).sendKeys("clarabecker");
        driver.findElement(By.id("senha")).sendKeys("senhaErrada123"); // Senha incorreta

        // Clica no botão "Entrar" (submit)
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        Thread.sleep(1000);

        // Verifica se a mensagem de erro específica do React apareceu
        WebElement msgErro = driver.findElement(By.xpath("//*[contains(text(), 'Usuário ou senha inválidos.')]"));
        Assert.assertEquals("Usuário ou senha inválidos.", msgErro.getText());
    }
}