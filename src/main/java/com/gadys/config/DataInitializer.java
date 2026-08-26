package com.gadys.config;

import com.gadys.model.Local;
import com.gadys.model.StatusLocal;
import com.gadys.model.TipoUsuario;
import com.gadys.model.Usuario;
import com.gadys.repository.LocalRepository;
import com.gadys.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private LocalRepository localRepository;

    @Override
    public void run(String... args) throws Exception {
        try {
            seedUsuarios();
            seedLocais();
            seedInformacoesAdicionais();
        } catch (Exception e) {
            System.err.println("DataInitializer falhou: " + e.getMessage());
        }
    }

    private void seedUsuarios() {
        if (!usuarioService.existeEmail("admin@gadys.com")) {
            Usuario admin = new Usuario("Administrador", "admin@gadys.com", "123456");
            admin.setTipoUsuario(TipoUsuario.ADM);
            usuarioService.salvar(admin);
        }
        if (!usuarioService.existeEmail("gadys2026@gmail.com")) {
            Usuario admin2 = new Usuario("Admin GADYS", "gadys2026@gmail.com", "123456");
            admin2.setTipoUsuario(TipoUsuario.ADM);
            usuarioService.salvar(admin2);
        }
        if (!usuarioService.existeEmail("usuario@gadys.com")) {
            usuarioService.salvar(new Usuario("Usuário Teste", "usuario@gadys.com", "123456"));
        }
    }

    // { nome, descricao, categoria, subcategoria, cidade, estado(sigla), rotaFrontend }
    private void seedLocais() {
        List<Object[]> locais = List.of(

            // AMAZONAS - usa sigla "AM" no useLocaisAtivos
            new Object[]{"Encontro das Águas", "Fenômeno natural onde as águas do Rio Negro e do Rio Solimões correm lado a lado por 6km sem se misturar.", "lugares-visitar", "lugares-paradisiacos", "Manaus", "AM", "/encontro-aguas"},
            new Object[]{"Teatro Amazonas", "Majestoso teatro construído durante o ciclo da borracha, inaugurado em 1896. Símbolo da cultura de Manaus.", "lugares-visitar", "monumentos", "Manaus", "AM", "/teatro-amazonas"},
            new Object[]{"Amazônico Peixaria Regional", "Restaurante tradicional de Manaus especializado em peixes amazônicos como pirarucu, tambaqui e tucunaré.", "curiosidades", "restaurantes", "Manaus", "AM", "/amazonico-peixaria"},
            new Object[]{"Arquipélago de Anavilhanas", "Um dos maiores arquipélagos fluviais do mundo, com centenas de ilhas no Rio Negro.", "lugares-visitar", "lugares-paradisiacos", "Novo Airão", "AM", "/arquipelago-anavilhanas"},
            new Object[]{"Bumbódromo", "Palco do maior festival folclórico do Brasil. Arena a céu aberto com capacidade para 35 mil pessoas em Parintins.", "lugares-visitar", "monumentos", "Parintins", "AM", "/bumbodromo"},
            new Object[]{"Cachoeira do Santuário", "Uma das mais belas quedas d'água da Amazônia, com piscinas naturais em Presidente Figueiredo.", "lugares-visitar", "lugares-paradisiacos", "Presidente Figueiredo", "AM", "/cachoeira-santuario"},
            new Object[]{"Coreto Peixaria & Café Regional", "Restaurante charmoso de Manaus que une peixaria amazônica com café regional.", "curiosidades", "restaurantes", "Manaus", "AM", "/coreto-peixaria"},
            new Object[]{"Ponte Rio Negro", "Ponte estaiada com 3,5 km conectando Manaus a Iranduba sobre o Rio Negro.", "lugares-visitar", "monumentos", "Manaus", "AM", "/ponte-rio-negro"},


            // RIO DE JANEIRO - sigla "RJ"
            new Object[]{"Cristo Redentor", "Uma das Sete Maravilhas do Mundo Moderno, com 38 metros no topo do Corcovado.", "lugares-visitar", "monumentos", "Rio de Janeiro", "RJ", null},
            new Object[]{"Pão de Açúcar", "Dois morros com teleférico e vista panorâmica da Baía de Guanabara.", "lugares-visitar", "lugares-paradisiacos", "Rio de Janeiro", "RJ", null},
            new Object[]{"Theatro Municipal", "Símbolo cultural do Rio, inaugurado em 1909 com arquitetura eclética.", "curiosidades", "costume-cultural", "Rio de Janeiro", "RJ", null},
            new Object[]{"Escadaria Selarón", "Obra do artista chileno Jorge Selarón, com mais de 2.000 azulejos coloridos.", "curiosidades", "costume-cultural", "Rio de Janeiro", "RJ", null},
            new Object[]{"Arcos da Lapa", "Aqueduto do século XVIII, símbolo do bairro boêmio da Lapa.", "lugares-visitar", "monumentos", "Rio de Janeiro", "RJ", null},
            new Object[]{"Museu do Amanhã", "Museu de ciências futuristas projetado por Santiago Calatrava na Zona Portuária.", "curiosidades", "costume-cultural", "Rio de Janeiro", "RJ", null},
            new Object[]{"Praia de Copacabana", "A praia mais famosa do mundo, com 4 km de extensão no coração do Rio.", "lugares-visitar", "lugares-paradisiacos", "Rio de Janeiro", "RJ", null},
            new Object[]{"Praia de Ipanema", "Praia elegante e cosmopolita, inspiração da Garota de Ipanema.", "lugares-visitar", "lugares-paradisiacos", "Rio de Janeiro", "RJ", null},
            new Object[]{"Floresta da Tijuca", "Maior floresta urbana do mundo, com cachoeiras e trilhas no coração do Rio.", "lugares-visitar", "lugares-paradisiacos", "Rio de Janeiro", "RJ", null},
            new Object[]{"Lagoa Rodrigo de Freitas", "Lagoa natural cercada de montanhas, parques e restaurantes.", "lugares-visitar", "lugares-paradisiacos", "Rio de Janeiro", "RJ", null},
            new Object[]{"Jardim Botânico", "Um dos mais importantes do mundo, com 6.500 espécies de plantas.", "lugares-visitar", "lugares-paradisiacos", "Rio de Janeiro", "RJ", null},
            new Object[]{"Parque Lage", "Parque histórico com mansão, escola de artes visuais e trilhas para o Corcovado.", "lugares-visitar", "lugares-paradisiacos", "Rio de Janeiro", "RJ", null},

            // SÃO PAULO - sigla "SP"
            new Object[]{"MASP", "O Museu de Arte de São Paulo é um dos mais importantes da América Latina, com acervo de 11 mil obras.", "curiosidades", "costume-cultural", "São Paulo", "SP", null},
            new Object[]{"Teatro Municipal", "Inaugurado em 1911, símbolo cultural de SP com arquitetura inspirada na Ópera de Paris.", "curiosidades", "costume-cultural", "São Paulo", "SP", null},
            new Object[]{"Mercadão", "O Mercado Municipal de São Paulo, famoso pelo sanduíche de mortadela.", "curiosidades", "restaurantes", "São Paulo", "SP", null},
            new Object[]{"Edifício Copan", "Ícone da arquitetura modernista projetado por Oscar Niemeyer com forma ondulada única.", "lugares-visitar", "monumentos", "São Paulo", "SP", null},
            new Object[]{"Parque Ibirapuera", "O Central Park paulistano, com museus, lagos e espaços de lazer.", "lugares-visitar", "lugares-paradisiacos", "São Paulo", "SP", null},
            new Object[]{"Pinacoteca", "Museu de artes visuais mais antigo de SP, fundado em 1905, com foco na produção brasileira.", "curiosidades", "costume-cultural", "São Paulo", "SP", null},
            new Object[]{"Avenida Paulista", "O coração financeiro e cultural de SP. Aos domingos vira calçadão com feiras e artistas.", "lugares-visitar", "monumentos", "São Paulo", "SP", null},
            new Object[]{"Beco do Batman", "Famoso beco coberto de grafites e arte urbana na Vila Madalena.", "curiosidades", "costume-cultural", "Vila Madalena", "SP", null},

            // CEARÁ - sigla "CE"
            new Object[]{"Jericoacoara", "Vila de pescadores famosa pela duna do pôr do sol e lagoas de água doce.", "lugares-visitar", "lugares-paradisiacos", "Jijoca de Jericoacoara", "CE", "/ceara/jericoacoara"},
            new Object[]{"Canoa Quebrada", "Praia com falésias vermelhas, dunas e lagoas. Destino boêmio com vida noturna.", "lugares-visitar", "lugares-paradisiacos", "Aracati", "CE", "/ceara/canoa-quebrada"},
            new Object[]{"Centro Dragão do Mar", "Centro cultural de Fortaleza com teatro, museu, planetário e espaços de arte.", "lugares-visitar", "monumentos", "Fortaleza", "CE", "/ceara/dragao-do-mar"},
            new Object[]{"Beach Park", "Maior parque aquático da América Latina em Aquiraz.", "lugares-visitar", "lugares-paradisiacos", "Aquiraz", "CE", "/ceara/beach-park"},
            new Object[]{"Praia do Futuro", "Principal praia urbana de Fortaleza, famosa pelas barracas de frutos do mar.", "lugares-visitar", "lugares-paradisiacos", "Fortaleza", "CE", "/ceara/praia-do-futuro"},
            new Object[]{"Serra de Baturité", "Maciço montanhoso com clima ameno, cachoeiras e plantações de café.", "lugares-visitar", "lugares-paradisiacos", "Guaramiranga", "CE", "/ceara/serra-de-baturite"},
            new Object[]{"Chapada do Araripe", "Planalto com sítios paleontológicos únicos. Abriga o Geopark Araripe.", "lugares-visitar", "lugares-paradisiacos", "Crato", "CE", "/ceara/chapada-do-araripe"},
            new Object[]{"Centro Histórico de Fortaleza", "Conjunto histórico com Catedral, Theatro José de Alencar e Mercado Central.", "lugares-visitar", "monumentos", "Fortaleza", "CE", "/ceara/centro-historico-fortaleza"},

            // PARÁ - sigla "PA"
            new Object[]{"Alter do Chão", "Conhecida como o Caribe Amazônico, com praias de areia branca no Rio Tapajós.", "lugares-visitar", "lugares-paradisiacos", "Santarém", "PA", null},
            new Object[]{"Ilha de Marajó", "Maior arquipélago fluviomarinho do mundo, famoso por búfalos e cultura única.", "lugares-visitar", "lugares-paradisiacos", "Arquipélago de Marajó", "PA", null},
            new Object[]{"Parque Nacional da Amazônia", "Vasta área de floresta protegida ideal para ecoturismo e trilhas.", "lugares-visitar", "lugares-paradisiacos", "Itaituba", "PA", null},
            new Object[]{"Theatro da Paz", "Um dos mais luxuosos do Brasil, símbolo do Ciclo da Borracha em Belém.", "lugares-visitar", "monumentos", "Belém", "PA", null},
            new Object[]{"Mercado Ver-o-Peso", "Maior mercado ao ar livre da América Latina com ervas, frutos e peixes.", "lugares-visitar", "monumentos", "Belém", "PA", null},
            new Object[]{"Forte do Presépio", "Marco da fundação de Belém com museu e vista da Baía do Guajará.", "lugares-visitar", "monumentos", "Belém", "PA", null},

            // MINAS GERAIS - sigla "MG"
            new Object[]{"Ouro Preto", "Cidade histórica Patrimônio da UNESCO com arquitetura barroca do século XVIII.", "lugares-visitar", "monumentos", "Ouro Preto", "MG", "/mg/ouro-preto"},
            new Object[]{"Instituto Inhotim", "Maior museu de arte contemporânea a céu aberto do mundo em Brumadinho.", "lugares-visitar", "monumentos", "Brumadinho", "MG", "/mg/inhotim"},
            new Object[]{"Tiradentes", "Cidade histórica com arquitetura colonial preservada e gastronomia mineira.", "lugares-visitar", "monumentos", "Tiradentes", "MG", null},
            new Object[]{"Diamantina", "Cidade Patrimônio da UNESCO, berço de JK, com casarões coloniais e serras.", "lugares-visitar", "monumentos", "Diamantina", "MG", null},
            new Object[]{"Santuário do Bom Jesus de Matosinhos", "Importante santuário religioso em Congonhas com esculturas de Aleijadinho.", "lugares-visitar", "monumentos", "Congonhas", "MG", null},
            new Object[]{"Carnaval de Belo Horizonte", "Carnaval de rua com blocos tradicionais e grande diversidade cultural.", "curiosidades", "costume-cultural", "Belo Horizonte", "MG", null},

            // ESPÍRITO SANTO - sigla "ES"
            new Object[]{"Pedra Azul", "Monólito de granito com 1.822 metros no Parque Estadual da Pedra Azul.", "lugares-visitar", "lugares-paradisiacos", "Domingos Martins", "ES", "/es/pedra-azul"},
            new Object[]{"Guarapari", "Cidade litorânea famosa pelas praias de areia monazítica com propriedades terapêuticas.", "lugares-visitar", "lugares-paradisiacos", "Guarapari", "ES", "/es/guarapari"},
            new Object[]{"Convento da Penha", "Um dos santuários mais antigos do Brasil, construído no século XVI em Vila Velha.", "lugares-visitar", "monumentos", "Vila Velha", "ES", null},
            new Object[]{"Regência Augusta", "Vila de pescadores na foz do Rio Doce, famosa pela soltura de tartarugas marinhas.", "lugares-visitar", "lugares-paradisiacos", "Linhares", "ES", null},
            new Object[]{"Domingos Martins", "Cidade serrana com influência alemã, clima frio e arquitetura europeia.", "lugares-visitar", "lugares-paradisiacos", "Domingos Martins", "ES", null},
            new Object[]{"Santa Teresa", "Cidade serrana com museu de biologia, cachoeiras e colônia italiana.", "lugares-visitar", "lugares-paradisiacos", "Santa Teresa", "ES", null},
            new Object[]{"Anchieta", "Cidade histórica com a Igreja de São Tiago e o Santuário Nacional de Anchieta.", "lugares-visitar", "monumentos", "Anchieta", "ES", null},

            // ACRE - sigla "AC" (AcrePontos não usa useLocaisAtivos, mas mantemos para consistência)
            new Object[]{"Parque Estadual Chandless", "695 mil hectares de floresta amazônica intocada na fronteira com o Peru.", "lugares-visitar", "lugares-paradisiacos", "Santa Rosa do Purus", "AC", "/acre/parque-chandless"},
            new Object[]{"Centro Histórico de Rio Branco", "Ponte Metálica, Palácio Rio Branco e Mercado Velho às margens do Rio Acre.", "lugares-visitar", "monumentos", "Rio Branco", "AC", "/acre/centro-historico"},

            // AMAPÁ
            new Object[]{"Fortaleza de São José de Macapá", "Maior fortaleza do Brasil colonial, construída entre 1764 e 1782.", "lugares-visitar", "monumentos", "Macapá", "AP", "/amapa/fortaleza-sao-jose"},

            // RONDÔNIA
            new Object[]{"Museu Estrada de Ferro Madeira-Mamoré", "A Ferrovia do Diabo, construída entre 1907 e 1912. Patrimônio histórico em Porto Velho.", "lugares-visitar", "monumentos", "Porto Velho", "RO", "/rondonia/ferrovia-madeira-mamore"},

            // RORAIMA
            new Object[]{"Monte Roraima", "Tepui com 2.875 metros na fronteira entre Brasil, Venezuela e Guiana.", "lugares-visitar", "lugares-paradisiacos", "Uiramutã", "RR", "/roraima/monte-roraima"},

            // TOCANTINS
            new Object[]{"Parque Estadual do Jalapão", "Região com dunas, fervedouros, cachoeiras e rios cristalinos no cerrado.", "lugares-visitar", "lugares-paradisiacos", "Mateiros", "TO", "/tocantins/jalapao"},

            // BAHIA
            new Object[]{"Pelourinho", "Centro histórico de Salvador, Patrimônio da UNESCO com arquitetura colonial.", "lugares-visitar", "monumentos", "Salvador", "BA", null},

            // PERNAMBUCO
            new Object[]{"Fernando de Noronha", "Arquipélago com 21 ilhas, Patrimônio Natural da Humanidade e melhor destino de mergulho.", "lugares-visitar", "lugares-paradisiacos", "Fernando de Noronha", "PE", null},

            // MATO GROSSO DO SUL
            new Object[]{"Pantanal", "Maior planície alagável do mundo com 150 mil km² e maior concentração de fauna.", "lugares-visitar", "lugares-paradisiacos", "Corumbá", "MS", null},

            // PARANÁ
            new Object[]{"Cataratas do Iguaçu", "275 quedas d'água na fronteira Brasil-Argentina. Patrimônio Natural da UNESCO.", "lugares-visitar", "lugares-paradisiacos", "Foz do Iguaçu", "PR", null}
        );

        for (Object[] dados : locais) {
            String nome = (String) dados[0];
            String rotaFrontend = (String) dados[6];

            boolean jaExiste = rotaFrontend != null
                ? localRepository.findByRotaFrontend(rotaFrontend).isPresent()
                : localRepository.findByNomeIgnoreCase(nome).isPresent();

            if (jaExiste) continue;

            Local local = new Local();
            local.setNome(nome);
            local.setDescricao((String) dados[1]);
            local.setCategoria((String) dados[2]);
            local.setSubcategoria((String) dados[3]);
            local.setCidade((String) dados[4]);
            local.setEstado((String) dados[5]);
            local.setRotaFrontend(rotaFrontend);
            local.setStatus(StatusLocal.ATIVO);
            local.setEnviadoPor("GADYS");
            localRepository.save(local);
        }

        System.out.println("DataInitializer: locais verificados/inseridos com sucesso.");
    }

    /**
     * Popula informacoesAdicionais (JSON rico) para locais com página própria.
     * Só atualiza se o campo ainda estiver null — preserva edições feitas pelo admin.
     */
    private static String secoes(String... pares) {
        StringBuilder sb = new StringBuilder("{\"secoes\":{\"fotos\":{\"label\":\"Fotos\"},\"avaliacoes\":{\"label\":\"Avalia\u00e7\u00f5es\"}");
        for (int i = 0; i < pares.length; i += 2) sb.append(",").append(pares[i]).append(":").append(pares[i+1]);
        sb.append("}}");
        return sb.toString();
    }

    private void seedInformacoesAdicionais() {
        // ── AMAZONAS ──
        seedInfoLocal("/encontro-aguas",
            "{\"carouselImages\":[\"/images/geral/en1-Am.jpg\",\"/images/geral/en2-Am.jpg\"]," +
            "\"galleryImages\":[{\"src\":\"/images/geral/en6-Am.jpg\"},{\"src\":\"/images/geral/en7-Am.webp\"}," +
            "{\"src\":\"/images/geral/en8-Am.webp\"},{\"src\":\"/images/geral/en5-Am.jpg\"}," +
            "{\"src\":\"/images/geral/en4-Am.webp\"},{\"src\":\"/images/geral/en3-Am.jpg\"}]," +
            "\"secoes\":{" +
            "\"fenomeno\":{\"label\":\"O Fenômeno\",\"titulo\":\"Um Balé de Águas no Coração do Mundo\"," +
            "\"texto\":\"Imagine estar no ponto exato onde dois gigantes se encontram. De um lado, o Rio Negro, com suas águas escuras e quentes. Do outro, o Rio Solimões, barrento e frio. Por mais de seis quilômetros, eles correm juntos, mas se recusam a misturar, criando uma linha divisória que desafia a lógica.\"," +
            "\"imagem\":\"/images/geral/en9-Am.webp\"," +
            "\"lista\":[\"Rio Negro: Quente (~28°C), lento (~2 km/h) e misteriosamente escuro.\",\"Rio Solimões: Frio (~22°C), rápido (~6 km/h) e imponentemente barrento.\"]}," +
            "\"experiencias\":{\"label\":\"Experiências Únicas\",\"titulo\":\"Sinta a Magia com Suas Próprias Mãos\"," +
            "\"texto\":\"Esta não é uma jornada para apenas observar. Navegue sobre a linha divisória e sinta a mudança abrupta de temperatura ao tocar as águas. Veja botos cor-de-rosa e tucuxis, os guardiões do rio, dançando ao redor do seu barco.\"," +
            "\"imagem\":\"/images/geral/en11-Am.jpg\"}," +
            "\"visita\":{\"label\":\"Visite\",\"titulo\":\"Sua Jornada Começa Agora\"," +
            "\"texto\":\"Transformar o sonho de conhecer o Encontro das Águas em realidade é mais fácil do que você imagina. Os passeios partem diariamente de Manaus.\"," +
            "\"imagem\":\"/images/geral/en12-Am.jpg\"," +
            "\"imagensExtras\":[\"/images/geral/en13-Am.jpg\",\"/images/geral/en14-Am.jpg\"]," +
            "\"subsecoes\":[" +
            "{\"titulo\":\"Como Chegar ao Coração da Amazônia\",\"texto\":\"O fenômeno está a apenas 10 km de Manaus. Agências de turismo locais oferecem passeios de lancha ou em barcos regionais.\"}," +
            "{\"titulo\":\"Sua Aventura com o Melhor Custo-Benefício\",\"texto\":\"Com opções que variam de R$ 90 a R$ 300 por pessoa. A maioria dos barcos parte pela manhã (9h), retornando no fim da tarde.\"}," +
            "{\"titulo\":\"Prepare-se para o Dia Perfeito\",\"texto\":\"O essencial: protetor solar, chapéu, óculos de sol e repelente. Não se esqueça da câmera e leve dinheiro em espécie para artesanato local.\"}]," +
            "\"recomendacoes\":[" +
            "{\"titulo\":\"Agências Confiáveis\",\"itens\":[{\"nome\":\"Amazon Tour\",\"nota\":4.9,\"contato\":\"(11) 4858-1141\",\"site\":\"https://amazontourmanaus.com/\"},{\"nome\":\"Manaus Jungle Tours\",\"nota\":4.8,\"contato\":\"(92) 99186-7133\",\"site\":\"https://www.manausjungletours.com/\"}]}," +
            "{\"titulo\":\"Onde se Hospedar\",\"itens\":[{\"nome\":\"Hotel Villa Amazônia\",\"nota\":4.7,\"contato\":\"(92) 99156-5369\",\"site\":\"https://villaamazonia.com/pt/\"},{\"nome\":\"Juma Ópera Hotel\",\"nota\":4.3,\"contato\":\"(92) 99137-4260\",\"site\":\"https://www.jumaopera.com.br/\"}]}]}," +
            "\"fotos\":{\"label\":\"Fotos\"},\"avaliacoes\":{\"label\":\"Avaliações\"}}}");

        seedInfoLocal("/teatro-amazonas",
            "{\"carouselImages\":[\"/images/geral/ta-am.jpg\",\"/images/geral/tea-am1.jpg\"]," +
            "\"galleryImages\":[{\"src\":\"/images/geral/ta-am.jpg\"},{\"src\":\"/images/geral/tea-am1.jpg\"},{\"src\":\"/images/geral/tea-am3.jpg\"},{\"src\":\"/images/geral/tea-am4.jpg\"},{\"src\":\"/images/geral/tea-am5.jpg\"},{\"src\":\"/images/geral/tea-am8.jpg\"},{\"src\":\"/images/geral/tea-am9.jpg\"},{\"src\":\"/images/geral/tea-am10.jpg\"},{\"src\":\"/images/geral/tea-am11.jpg\"}]," +
            "\"secoes\":{" +
            "\"historia\":{\"label\":\"A História\",\"titulo\":\"Um Palácio Erguido na Selva\"," +
            "\"texto\":\"No coração de Manaus, onde a floresta encontra a cidade, ergue-se uma das obras mais audaciosas da história brasileira. O Teatro Amazonas foi construído entre 1884 e 1896, no auge do ciclo da borracha, quando a Amazônia era o centro do mundo. Seus materiais vieram da Europa: mármore de Carrara, ferro da Escócia, cerâmica de Lisboa. Uma declaração de riqueza e poder que desafiou a lógica de seu tempo.\"," +
            "\"imagem\":\"/images/geral/tea-am3.jpg\"," +
            "\"lista\":[\"Construção: Entre 1884 e 1896, durante o ciclo da borracha.\",\"Cúpula: Revestida com 36.000 telhas nas cores da bandeira do Brasil.\",\"Materiais: Importados da Europa — mármore, ferro, cerâmica e cristal.\"]}," +
            "\"arquitetura\":{\"label\":\"Arquitetura\",\"titulo\":\"Detalhes que Contam uma Época\"," +
            "\"texto\":\"Cada centímetro do Teatro Amazonas é uma obra de arte. A cúpula, revestida com 36.000 telhas de cerâmica nas cores verde, ouro e azul da bandeira brasileira, domina o horizonte de Manaus. O interior revela um salão nobre com capacidade para 701 pessoas, decorado com pinturas alegóricas ao teto, lustres de cristal Murano e cadeiras de madeira nobre. O palco principal, com 859 m², já recebeu as maiores companhias de ópera do mundo.\"," +
            "\"imagem\":\"/images/geral/tea-am4.jpg\"," +
            "\"subsecoes\":[{\"titulo\":\"A Cúpula Icônica\",\"texto\":\"A cúpula é o símbolo máximo do teatro. Suas 36.000 telhas de cerâmica portuguesa formam um mosaico nas cores da bandeira brasileira, visível de vários pontos da cidade. À noite, iluminada, ela transforma o centro histórico de Manaus em um cenário de conto de fadas.\"}," +
            "{\"titulo\":\"O Salão Nobre\",\"texto\":\"O coração do teatro é seu salão principal, decorado com pinturas do artista italiano Domenico De Angelis. O teto retrata a lenda de Iara, a sereia amazônica, em uma fusão única entre a cultura europeia e a mitologia local. Os lustres de cristal Murano completam o espetáculo visual.\"}," +
            "{\"titulo\":\"O Piso Flutuante\",\"texto\":\"Uma das curiosidades mais fascinantes é o piso da plateia, construído com madeira de lei sobre uma estrutura que permite uma leve flutuação. Isso garante uma acústica excepcional, tornando o Teatro Amazonas um dos mais acusticamente perfeitos do mundo.\"}]}," +
            "\"visita\":{\"label\":\"Visite\",\"titulo\":\"Viva a Experiência\"," +
            "\"texto\":\"Visitar o Teatro Amazonas é mergulhar em uma das histórias mais fascinantes do Brasil. Seja em uma visita guiada ou assistindo a um espetáculo ao vivo, a experiência é inesquecível.\"," +
            "\"imagem\":\"/images/geral/tea-am9.jpg\"," +
            "\"subsecoes\":[{\"titulo\":\"Visitas Guiadas\",\"texto\":\"As visitas guiadas acontecem de terça a domingo, das 9h às 17h. Com duração de aproximadamente 45 minutos, os guias conduzem os visitantes pelos bastidores, camarins, salão nobre e palco. O ingresso custa R$ 50 (inteira) e R$ 25 (meia). Crianças até 5 anos não pagam.\"}," +
            "{\"titulo\":\"Espetáculos e Temporadas\",\"texto\":\"O teatro mantém uma programação cultural intensa ao longo do ano, com óperas, balés, concertos sinfônicos e peças teatrais. O Festival Amazonas de Ópera, realizado anualmente em abril e maio, é o maior evento de ópera da América Latina e atrai artistas de todo o mundo.\"}," +
            "{\"titulo\":\"Como Chegar\",\"texto\":\"O Teatro Amazonas está localizado na Praça São Sebastião, no centro histórico de Manaus. É facilmente acessível de táxi, aplicativo de transporte ou ônibus. Recomenda-se chegar com antecedência para espetáculos, pois o estacionamento na região é limitado.\"}]," +
            "\"recomendacoes\":[{\"titulo\":\"Restaurantes Próximos\",\"itens\":[{\"nome\":\"Banzeiro Restaurante\",\"nota\":4.5,\"contato\":\"(92) 3234-1621\",\"site\":\"https://www.restaurantebanzeiro.com.br/\"},{\"nome\":\"Caxiri Restaurante\",\"nota\":4.6,\"contato\":\"(92) 98405-4769\",\"site\":\"https://www.instagram.com/caxirirestaurante/\"},{\"nome\":\"Choupana Restaurante\",\"nota\":4.2,\"contato\":\"(92) 3031-3009\",\"site\":\"https://www.choupanarestaurante.com.br/\"}]}," +
            "{\"titulo\":\"Hotéis Recomendados\",\"itens\":[{\"nome\":\"Juma Ópera Hotel\",\"nota\":4.3,\"contato\":\"(92) 99137-4260\",\"site\":\"https://www.jumaopera.com.br/\"},{\"nome\":\"Hotel Intercity Manaus\",\"nota\":4.7,\"contato\":\"(11) 5198-6936\",\"site\":\"https://www.intercityhoteis.com.br/\"},{\"nome\":\"Blue Tree Premium Manaus\",\"nota\":4.5,\"contato\":\"(92) 3303-2000\",\"site\":\"https://www.bluetree.com.br/\"}]}]}," +
            "\"fotos\":{\"label\":\"Fotos\"},\"avaliacoes\":{\"label\":\"Avaliações\"}}}");

        seedInfoLocal("/arquipelago-anavilhanas",
            "{\"carouselImages\":[\"/images/geral/am-an1.jpg\",\"/images/geral/am-an2.jpg\"]," +
            "\"galleryImages\":[{\"src\":\"/images/geral/am-an3.webp\"},{\"src\":\"/images/geral/am-an4.jpg\"},{\"src\":\"/images/geral/am-an5.jpg\"},{\"src\":\"/images/geral/am-an6.jpg\"}]," +
            "\"secoes\":{\"sobre\":{\"label\":\"Sobre\",\"titulo\":\"O Labirinto de Ilhas do Rio Negro\"," +
            "\"texto\":\"O Arquipélago de Anavilhanas é um dos maiores arquipélagos fluviais do mundo, com mais de 400 ilhas, ilhotas e paranás no Rio Negro. Durante a cheia, as copas das árvores emergem das águas escuras criando um cenário surreal.\"," +
            "\"imagem\":\"/images/natureza/anavilhas.jpeg\"}," +
            "\"biodiversidade\":{\"label\":\"Biodiversidade\",\"titulo\":\"Um Santuário da Vida Amazônica\"," +
            "\"texto\":\"O Parque Nacional de Anavilhanas abriga uma das maiores diversidades biológicas do planeta. Suas águas negras e florestas de igapó criam habitats únicos para espécies que não existem em nenhum outro lugar do mundo.\"," +
            "\"imagem\":\"/images/geral/am-an3.webp\"}," +
            "\"visita\":{\"label\":\"Visite\",\"titulo\":\"Informações Práticas\",\"texto\":\"\"," +
            "\"subsecoes\":[{\"titulo\":\"Como Chegar\",\"texto\":\"A 180 km de Manaus, pela AM-352. Passeios de barco saem de Novo Airão.\"}]}," +
            "\"fotos\":{\"label\":\"Fotos\"},\"avaliacoes\":{\"label\":\"Avaliações\"}}}");

        seedInfoLocal("/bumbodromo",
            "{\"carouselImages\":[\"/images/geral/am-bun1.avif\",\"/images/geral/am-bun2.jpeg\"]," +
            "\"galleryImages\":[{\"src\":\"/images/geral/am-bun3.jpeg\"},{\"src\":\"/images/geral/am-bun4.jpeg\"},{\"src\":\"/images/geral/am-bun5.jpg\"},{\"src\":\"/images/geral/am-bun6.jpeg\"}]," +
            "\"secoes\":{\"historia\":{\"label\":\"A História\",\"titulo\":\"A Arena do Maior Espetáculo da Amazônia\"," +
            "\"texto\":\"O Bumbódromo de Parintins, oficialmente chamado de Centro Cultural e Esportivo Amazonino Mendes, é o palco do maior festival folclórico do Brasil. Inaugurado em 1988, o estádio a céu aberto foi projetado em formato de cabeça de boi e tem capacidade para mais de 35 mil pessoas.\"," +
            "\"imagem\":\"/images/geral/am-bun1.avif\"}," +
            "\"visita\":{\"label\":\"Visite\",\"titulo\":\"Informações Práticas\",\"texto\":\"\"," +
            "\"subsecoes\":[{\"titulo\":\"Festival de Parintins\",\"texto\":\"Realizado no último fim de semana de junho. Reserve passagens e hospedagem com meses de antecedência.\"}]}," +
            "\"fotos\":{\"label\":\"Fotos\"},\"avaliacoes\":{\"label\":\"Avaliações\"}}}");

        seedInfoLocal("/cachoeira-santuario",
            "{\"carouselImages\":[\"/images/geral/am-cs1.jpg\",\"/images/geral/am-cs2.jpg\"]," +
            "\"galleryImages\":[{\"src\":\"/images/geral/am-cs3.jpg\"},{\"src\":\"/images/geral/am-cs4.jpg\"},{\"src\":\"/images/geral/am-cs5.jpg\"},{\"src\":\"/images/geral/am-cs6.jpg\"}]," +
            "\"secoes\":{\"sobre\":{\"label\":\"Sobre\",\"titulo\":\"A Joia Escondida da Amazônia\"," +
            "\"texto\":\"A Cachoeira do Santuário é uma das mais belas quedas d'água da Amazônia, localizada em Presidente Figueiredo, a 107 km de Manaus. Com piscinas naturais de água cristalina e trilhas na floresta, é um destino imperdível para os amantes da natureza.\"," +
            "\"imagem\":\"/images/geral/am-cs7.jpg\"}," +
            "\"natureza\":{\"label\":\"Natureza\",\"titulo\":\"Um Espetáculo de Água e Floresta\"," +
            "\"texto\":\"Presidente Figueiredo abriga mais de 100 cachoeiras catalogadas, e a Cachoeira do Santuário se destaca pela beleza singular de suas águas e pela trilha que leva até ela, repleta de fauna e flora amazônicas.\"," +
            "\"imagem\":\"/images/geral/am-cs2.jpg\"}," +
            "\"visita\":{\"label\":\"Visite\",\"titulo\":\"Informações Práticas\",\"texto\":\"\"," +
            "\"subsecoes\":[{\"titulo\":\"Como Chegar\",\"texto\":\"A 107 km de Manaus pela AM-010. Acesso por trilha de 1,5 km.\"}]}," +
            "\"fotos\":{\"label\":\"Fotos\"},\"avaliacoes\":{\"label\":\"Avaliações\"}}}");

        // ── CEARÁ ──
        seedInfoLocal("/ponte-rio-negro",
            "{\"carouselImages\":[\"/images/geral/am-pn1.jpg\",\"/images/geral/am-pn2.jpg\"]," +
            "\"galleryImages\":[{\"src\":\"/images/geral/am-pn3.jpg\"},{\"src\":\"/images/geral/am-pn4.jpg\"},{\"src\":\"/images/geral/am-pn5.jpg\"}]," +
            "\"secoes\":{\"sobre\":{\"label\":\"Sobre\",\"titulo\":\"A Ponte que Une o Amazonas\"," +
            "\"texto\":\"A Ponte Rio Negro, oficialmente chamada de Ponte Jornalista Phelippe Daou, é uma das maiores obras de engenharia da Amazônia. Com 3,5 km de extensão, a ponte estaiada conecta Manaus a Iranduba, cruzando o majestoso Rio Negro. Inaugurada em 2011.\"," +
            "\"imagem\":\"/images/geral/am-pn2.jpg\"}," +
            "\"engenharia\":{\"label\":\"Engenharia\",\"titulo\":\"Uma Obra Monumental\"," +
            "\"texto\":\"A Ponte Rio Negro é um marco da engenharia brasileira. Seu projeto desafiou as condições extremas da Amazônia — variação de até 14 metros no nível do rio, ventos intensos e solo instável — para entregar uma estrutura que une beleza e funcionalidade.\"," +
            "\"imagem\":\"/images/geral/am-pn3.jpg\"}," +
            "\"visita\":{\"label\":\"Visite\",\"titulo\":\"Como Chegar e o Que Ver\"," +
            "\"texto\":\"A Ponte Rio Negro pode ser apreciada de vários ângulos. O pôr do sol visto da ponte ou das margens é um dos espetáculos mais bonitos de Manaus.\"," +
            "\"imagem\":\"/images/geral/am-pn8.jpg\"," +
            "\"subsecoes\":[{\"titulo\":\"Como Chegar\",\"texto\":\"A ponte fica na AM-070, saindo de Manaus em direção a Iranduba. Acessível de carro ou ônibus.\"}]}," +
            "\"fotos\":{\"label\":\"Fotos\"},\"avaliacoes\":{\"label\":\"Avaliações\"}}}");

        // ── CEARÁ ──
        seedInfoLocal("/ceara/jericoacoara",
            "{\"carouselImages\":[\"/images/geral/jericoacoara.jpg\",\"/images/geral/jericoacoara.jpg\"]," +
            "\"galleryImages\":[{\"src\":\"/images/geral/jericoacoara.jpg\"},{\"src\":\"/images/geral/jericoacoara.jpg\"},{\"src\":\"/images/geral/jericoacoara.jpg\"}]," +
            "\"secoes\":{\"sobre\":{\"label\":\"Sobre\",\"titulo\":\"O Paraíso Escondido do Brasil\"," +
            "\"texto\":\"Jericoacoara, carinhosamente chamada de \\\"Jeri\\\", é uma vila paradisíaca encravada entre dunas, lagoas e o mar. Considerada uma das praias mais bonitas do mundo pela revista Condé Nast Traveler, o lugar encanta pela combinação única de paisagens selvagens, ventos constantes e um pôr do sol que para o tempo.\"," +
            "\"imagem\":\"/images/geral/jericoacoara.jpg\"," +
            "\"lista\":[\"Localização: Jijoca de Jericoacoara, a 300 km de Fortaleza.\",\"Destaque: Pôr do sol na Pedra Furada, um dos mais belos do mundo.\",\"Esportes: Kitesurf e windsurf entre os melhores do planeta.\"]}," +
            "\"experiencias\":{\"label\":\"Experiências\",\"titulo\":\"Viva Jeri ao Máximo\"," +
            "\"texto\":\"Jericoacoara oferece experiências únicas para todos os perfis de viajante. Das lagoas de água doce às dunas que parecem desertos, cada canto guarda uma surpresa.\"," +
            "\"imagem\":\"/images/geral/jericoacoara.jpg\"," +
            "\"subsecoes\":[{\"titulo\":\"Lagoa do Paraíso e Lagoa Azul\",\"texto\":\"As lagoas de água doce cristalina são o cartão-postal de Jeri. A Lagoa do Paraíso, com suas águas esverdeadas e transparentes, é perfeita para um mergulho refrescante.\"}," +
            "{\"titulo\":\"Kitesurf e Windsurf\",\"texto\":\"Os ventos constantes tornaram Jeri um dos melhores destinos do mundo para kitesurf e windsurf. Escolas locais oferecem aulas para iniciantes.\"}," +
            "{\"titulo\":\"Dunas e Pôr do Sol\",\"texto\":\"Subir a Duna do Pôr do Sol para assistir ao espetáculo diário é um ritual sagrado em Jeri. Centenas de pessoas se reúnem no topo para aplaudir o sol se despedindo.\"}]}," +
            "\"visita\":{\"label\":\"Visite\",\"titulo\":\"Como Chegar e Se Hospedar\"," +
            "\"texto\":\"Chegar a Jericoacoara faz parte da aventura. O acesso é feito por veículos 4x4 ou buggys, atravessando dunas e praias a partir de Jijoca de Jericoacoara.\"," +
            "\"imagem\":\"/images/geral/jericoacoara.jpg\"," +
            "\"subsecoes\":[{\"titulo\":\"Como Chegar\",\"texto\":\"De Fortaleza, pegue um ônibus ou van até Jijoca (aprox. 4h). De lá, 4x4s fazem o trajeto pelas dunas até a vila (30 min).\"}," +
            "{\"titulo\":\"Melhor Época\",\"texto\":\"De julho a dezembro, quando os ventos são mais fortes — ideal para kitesurf. De janeiro a junho, as lagoas ficam mais cheias.\"}]," +
            "\"recomendacoes\":[{\"titulo\":\"Onde Comer\",\"itens\":[{\"nome\":\"Restaurante Estoril\",\"nota\":4.8,\"contato\":\"(88) 3669-2066\",\"site\":\"https://www.instagram.com/estoril.jeri/\"},{\"nome\":\"Saborear Restaurante\",\"nota\":4.7,\"contato\":\"(88) 3669-2191\",\"site\":\"https://www.instagram.com/saboreariericoacoara/\"}]}," +
            "{\"titulo\":\"Onde Ficar\",\"itens\":[{\"nome\":\"Pousada Papagaio\",\"nota\":4.8,\"contato\":\"(88) 3669-2222\",\"site\":\"https://www.pousadapapagaio.com.br/\"},{\"nome\":\"Vila Kalango\",\"nota\":4.9,\"contato\":\"(88) 3669-2289\",\"site\":\"https://www.vilakalango.com.br/\"}]}]}," +
            "\"fotos\":{\"label\":\"Fotos\"},\"avaliacoes\":{\"label\":\"Avaliações\"}}}");

        seedInfoLocal("/ceara/canoa-quebrada",
            "{\"carouselImages\":[\"/images/geral/CE_-_Canoa_Quebrada_-_Fal\u00e9sia.jpg\",\"/images/geral/CE_-_Canoa_Quebrada_-_Fal\u00e9sia.jpg\"]," +
            "\"galleryImages\":[{\"src\":\"/images/geral/CE_-_Canoa_Quebrada_-_Fal\u00e9sia.jpg\"},{\"src\":\"/images/geral/CE_-_Canoa_Quebrada_-_Fal\u00e9sia.jpg\"},{\"src\":\"/images/geral/CE_-_Canoa_Quebrada_-_Fal\u00e9sia.jpg\"}]," +
            "\"secoes\":{\"sobre\":{\"label\":\"Sobre\",\"titulo\":\"As Falésias Vermelhas do Ceará\"," +
            "\"texto\":\"Canoa Quebrada é uma das praias mais famosas do Nordeste brasileiro. Suas imponentes falésias de arenito vermelho contrastam com a areia branca e o mar azul-turquesa, criando uma paisagem de tirar o fôlego. A vila, que já foi um vilarejo de pescadores, hoje é um destino cosmopolita com restaurantes, bares e uma vida noturna vibrante na famosa Broadway.\"," +
            "\"imagem\":\"/images/geral/CE_-_Canoa_Quebrada_-_Fal\u00e9sia.jpg\"," +
            "\"lista\":[\"Localização: Aracati, a 164 km de Fortaleza.\",\"Símbolo: A lua e a estrela esculpidas nas falésias.\",\"Destaque: A Broadway, rua principal com bares e restaurantes.\"]}," +
            "\"experiencias\":{\"label\":\"Experiências\",\"titulo\":\"O Que Fazer em Canoa Quebrada\"," +
            "\"texto\":\"De passeios de buggy pelas falésias a noites animadas na Broadway, Canoa Quebrada tem muito a oferecer para todos os gostos.\"," +
            "\"imagem\":\"/images/geral/CE_-_Canoa_Quebrada_-_Fal\u00e9sia.jpg\"," +
            "\"subsecoes\":[{\"titulo\":\"Passeio de Buggy\",\"texto\":\"Os buggys percorrem as falésias e praias vizinhas, como Majorlândia e Quixaba. É a forma mais emocionante de explorar a região e contemplar as formações rochosas de perto.\"}," +
            "{\"titulo\":\"A Broadway\",\"texto\":\"A rua principal de Canoa Quebrada é o coração da vida noturna. Repleta de bares, restaurantes e lojas de artesanato, ela ganha vida ao anoitecer com música ao vivo e forró.\"}," +
            "{\"titulo\":\"Esportes Aquáticos\",\"texto\":\"As condições de vento e mar fazem de Canoa Quebrada um ótimo destino para kitesurf, windsurf e stand-up paddle. Aulas e aluguel de equipamentos estão disponíveis na praia.\"}]}," +
            "\"visita\":{\"label\":\"Visite\",\"titulo\":\"Planeje Sua Visita\"," +
            "\"texto\":\"Canoa Quebrada é acessível e bem estruturada para receber turistas durante todo o ano.\"," +
            "\"imagem\":\"/images/geral/CE_-_Canoa_Quebrada_-_Fal\u00e9sia.jpg\"," +
            "\"subsecoes\":[{\"titulo\":\"Como Chegar\",\"texto\":\"De Fortaleza, há ônibus regulares até Aracati (2h30). De lá, táxis e vans fazem o trajeto até a praia (20 min). De carro, siga pela CE-040.\"}," +
            "{\"titulo\":\"Melhor Época\",\"texto\":\"De julho a dezembro é a alta temporada, com ventos fortes e céu limpo. De fevereiro a maio, o período chuvoso traz menos turistas e preços mais baixos.\"}]," +
            "\"recomendacoes\":[{\"titulo\":\"Onde Comer\",\"itens\":[{\"nome\":\"Restaurante Bucaneiro\",\"nota\":4.7,\"contato\":\"(88) 3421-7016\",\"site\":\"https://www.instagram.com/bucaneiro.cq/\"},{\"nome\":\"Long Beach Bar\",\"nota\":4.6,\"contato\":\"(88) 3421-7070\",\"site\":\"https://www.instagram.com/longbeachcq/\"}]}," +
            "{\"titulo\":\"Onde Ficar\",\"itens\":[{\"nome\":\"Hotel Iberostar Praia do Forte\",\"nota\":4.7,\"contato\":\"(88) 3421-7000\",\"site\":\"https://www.iberostar.com/\"},{\"nome\":\"Pousada Lua Estrela\",\"nota\":4.8,\"contato\":\"(88) 3421-7055\",\"site\":\"https://www.instagram.com/pousadaluaestrela/\"}]}]}," +
            "\"fotos\":{\"label\":\"Fotos\"},\"avaliacoes\":{\"label\":\"Avaliações\"}}}");

        seedInfoLocal("/ceara/dragao-do-mar",
            "{\"carouselImages\":[\"/images/geral/teatro-dragao-do-mar.jpg\",\"/images/geral/teatro-dragao-do-mar.jpg\"]," +
            "\"galleryImages\":[{\"src\":\"/images/geral/teatro-dragao-do-mar.jpg\"},{\"src\":\"/images/geral/teatro-dragao-do-mar.jpg\"},{\"src\":\"/images/geral/teatro-dragao-do-mar.jpg\"}]," +
            "\"secoes\":{\"sobre\":{\"label\":\"Sobre\",\"titulo\":\"Arte e Cultura no Coração de Fortaleza\"," +
            "\"texto\":\"O Centro Cultural Dragão do Mar é o maior complexo cultural do Ceará e um dos mais importantes do Brasil. Inaugurado em 1999, o espaço homenageia Francisco José do Nascimento, o \\\"Dragão do Mar\\\", jangadeiro cearense que se recusou a transportar escravos. Com arquitetura moderna e arrojada, o centro abriga museus, teatro, planetário, cinema e espaços de convivência.\"," +
            "\"imagem\":\"/images/geral/teatro-dragao-do-mar.jpg\"," +
            "\"lista\":[\"Localização: Praia de Iracema, Fortaleza - CE.\",\"Inauguração: 1999, projeto do arquiteto Fausto Nilo.\",\"Destaques: Museu de Arte Contemporânea, Planetário e Memorial da Cultura Cearense.\"]}," +
            "\"experiencias\":{\"label\":\"Experiências\",\"titulo\":\"O Que Explorar no Dragão do Mar\"," +
            "\"texto\":\"O complexo oferece uma programação cultural intensa e diversificada, com exposições, espetáculos e eventos ao longo de todo o ano.\"," +
            "\"imagem\":\"/images/geral/teatro-dragao-do-mar.jpg\"," +
            "\"subsecoes\":[{\"titulo\":\"Museu de Arte Contemporânea (MAC)\",\"texto\":\"O MAC Ceará ocupa um dos espaços mais emblemáticos do centro, com exposições permanentes e temporárias de artistas locais, nacionais e internacionais.\"}," +
            "{\"titulo\":\"Planetário Rubens de Azevedo\",\"texto\":\"Um dos mais modernos do Brasil, o planetário oferece sessões de astronomia que transportam o visitante para uma viagem pelo universo.\"}," +
            "{\"titulo\":\"Teatro e Anfiteatro\",\"texto\":\"O complexo conta com um teatro fechado e um anfiteatro a céu aberto, onde acontecem shows, peças teatrais e festivais culturais durante todo o ano.\"}]}," +
            "\"visita\":{\"label\":\"Visite\",\"titulo\":\"Como Visitar\"," +
            "\"texto\":\"O Centro Dragão do Mar está localizado na Praia de Iracema, uma das regiões mais charmosas de Fortaleza, com fácil acesso por transporte público.\"," +
            "\"imagem\":\"/images/geral/teatro-dragao-do-mar.jpg\"," +
            "\"subsecoes\":[{\"titulo\":\"Horários e Ingressos\",\"texto\":\"O centro funciona de terça a domingo, das 10h às 21h30. A entrada é gratuita para os espaços externos. Museus e planetário têm ingressos a partir de R$ 10.\"}," +
            "{\"titulo\":\"Como Chegar\",\"texto\":\"Localizado na Rua Dragão do Mar, 81, Praia de Iracema. Acessível por ônibus, metrô (estação José de Alencar) ou aplicativos de transporte.\"}]," +
            "\"recomendacoes\":[{\"titulo\":\"Onde Comer por Perto\",\"itens\":[{\"nome\":\"Restaurante Colher de Pau\",\"nota\":4.8,\"contato\":\"(85) 3219-3773\",\"site\":\"https://www.colherdepau.com.br/\"},{\"nome\":\"Coco Bambu Iracema\",\"nota\":4.6,\"contato\":\"(85) 3198-4747\",\"site\":\"https://www.cocobambu.com.br/\"}]}," +
            "{\"titulo\":\"Onde Ficar\",\"itens\":[{\"nome\":\"Gran Marquise Hotel\",\"nota\":4.8,\"contato\":\"(85) 3466-5000\",\"site\":\"https://www.granmarquise.com.br/\"},{\"nome\":\"Hotel Luzeiros Fortaleza\",\"nota\":4.7,\"contato\":\"(85) 4006-8585\",\"site\":\"https://www.luzeiroshotel.com.br/\"}]}]}," +
            "\"fotos\":{\"label\":\"Fotos\"},\"avaliacoes\":{\"label\":\"Avaliações\"}}}");

        seedInfoLocal("/ceara/beach-park",
            "{\"carouselImages\":[\"/images/geral/beach.webp\",\"/images/geral/beach.webp\"]," +
            "\"galleryImages\":[{\"src\":\"/images/geral/beach.webp\"},{\"src\":\"/images/geral/beach.webp\"},{\"src\":\"/images/geral/beach.webp\"}]," +
            "\"secoes\":{\"sobre\":{\"label\":\"Sobre\",\"titulo\":\"Diversão Sem Limites no Ceará\"," +
            "\"texto\":\"O Beach Park é o maior parque aquático da América Latina e um dos maiores do mundo. Localizado em Aquiraz, a apenas 27 km de Fortaleza, o complexo reúne toboáguas radicais, piscinas de ondas, atrações para crianças e uma praia privativa com estrutura completa. É o destino perfeito para famílias que buscam adrenalina e diversão.\"," +
            "\"imagem\":\"/images/geral/beach.webp\"," +
            "\"lista\":[\"Localização: Aquiraz, a 27 km de Fortaleza.\",\"Área: Mais de 70.000 m² de atrações aquáticas.\",\"Destaque: Insano, o toboágua mais alto do mundo por anos.\"]}," +
            "\"experiencias\":{\"label\":\"Experiências\",\"titulo\":\"Adrenalina e Diversão para Todos\"," +
            "\"texto\":\"Com mais de 20 atrações aquáticas, o Beach Park tem opções para todas as idades e perfis de visitantes.\"," +
            "\"imagem\":\"/images/geral/beach.webp\"," +
            "\"subsecoes\":[{\"titulo\":\"Insano e Atrações Radicais\",\"texto\":\"O Insano, com seus 41 metros de altura, foi por anos o toboágua mais alto do mundo. Para os amantes de adrenalina, há também o Boomerang, o Aqualoop e o Master Blaster.\"}," +
            "{\"titulo\":\"Área Infantil\",\"texto\":\"O Acqua Kids é um paraíso para as crianças, com toboáguas menores, piscinas rasas e brinquedos aquáticos seguros e supervisionados.\"}," +
            "{\"titulo\":\"Praia Privativa\",\"texto\":\"O complexo conta com uma praia privativa com cadeiras, guarda-sóis e serviço de praia completo, perfeita para relaxar após as atrações.\"}]}," +
            "\"visita\":{\"label\":\"Visite\",\"titulo\":\"Planeje Seu Dia no Beach Park\"," +
            "\"texto\":\"Para aproveitar ao máximo, chegue cedo e planeje as atrações com antecedência.\"," +
            "\"imagem\":\"/images/geral/beach.webp\"," +
            "\"subsecoes\":[{\"titulo\":\"Ingressos e Horários\",\"texto\":\"O parque funciona de quarta a domingo e feriados, das 11h às 17h. Ingressos a partir de R$ 180 (adulto) e R$ 90 (meia). Compre online com antecedência para garantir desconto.\"}," +
            "{\"titulo\":\"Como Chegar\",\"texto\":\"De Fortaleza, há ônibus direto saindo do Terminal Papicu. De carro, siga pela CE-040 em direção a Aquiraz. O parque oferece estacionamento amplo.\"}]," +
            "\"recomendacoes\":[{\"titulo\":\"Onde Comer no Complexo\",\"itens\":[{\"nome\":\"Beach Park Suites Resort\",\"nota\":4.7,\"contato\":\"(85) 4012-3000\",\"site\":\"https://www.beachpark.com.br/\"},{\"nome\":\"Restaurante Âncora\",\"nota\":4.5,\"contato\":\"(85) 4012-3100\",\"site\":\"https://www.beachpark.com.br/\"}]}," +
            "{\"titulo\":\"Onde Ficar\",\"itens\":[{\"nome\":\"Beach Park Suites Resort\",\"nota\":4.8,\"contato\":\"(85) 4012-3000\",\"site\":\"https://www.beachpark.com.br/\"},{\"nome\":\"Beach Park Oceani Hotel\",\"nota\":4.7,\"contato\":\"(85) 4012-3200\",\"site\":\"https://www.beachpark.com.br/\"}]}]}," +
            "\"fotos\":{\"label\":\"Fotos\"},\"avaliacoes\":{\"label\":\"Avaliações\"}}}");

        seedInfoLocal("/ceara/praia-do-futuro",
            "{\"carouselImages\":[\"/images/geral/Futuro.jpeg\",\"/images/geral/Futuro.jpeg\"]," +
            "\"galleryImages\":[{\"src\":\"/images/geral/Futuro.jpeg\"},{\"src\":\"/images/geral/Futuro.jpeg\"},{\"src\":\"/images/geral/Futuro.jpeg\"}]," +
            "\"secoes\":{\"sobre\":{\"label\":\"Sobre\",\"titulo\":\"O Coração das Praias de Fortaleza\"," +
            "\"texto\":\"A Praia do Futuro é a mais popular e movimentada de Fortaleza. Com 8 km de extensão, águas mornas e barracas estruturadas, ela é o destino favorito dos fortalezenses para um dia de sol e mar. As famosas barracas de frutos do mar, com música ao vivo e serviço completo, são o grande atrativo do lugar.\"," +
            "\"imagem\":\"/images/geral/Futuro.jpeg\"," +
            "\"lista\":[\"Localização: Zona Leste de Fortaleza, a 8 km do centro.\",\"Extensão: 8 km de praia com estrutura completa.\",\"Destaque: Barracas de frutos do mar com música ao vivo.\"]}," +
            "\"experiencias\":{\"label\":\"Experiências\",\"titulo\":\"Um Dia Perfeito na Praia do Futuro\"," +
            "\"texto\":\"A Praia do Futuro oferece muito mais do que sol e mar. As barracas são verdadeiros complexos de lazer com piscinas, shows e gastronomia.\"," +
            "\"imagem\":\"/images/geral/Futuro.jpeg\"," +
            "\"subsecoes\":[{\"titulo\":\"As Barracas Famosas\",\"texto\":\"As barracas da Praia do Futuro são famosas em todo o Brasil. Chico do Caranguejo, Crocobeach e Barraca do Meio são algumas das mais tradicionais, com frutos do mar frescos e música ao vivo.\"}," +
            "{\"titulo\":\"Esportes e Lazer\",\"texto\":\"A praia é ideal para vôlei de praia, futebol, stand-up paddle e natação. As águas mornas e relativamente calmas tornam o banho de mar muito agradável.\"}," +
            "{\"titulo\":\"Gastronomia\",\"texto\":\"Os frutos do mar são o ponto forte da Praia do Futuro. Caranguejos, lagostas, camarões e peixes frescos são preparados na hora pelas barracas locais.\"}]}," +
            "\"visita\":{\"label\":\"Visite\",\"titulo\":\"Como Aproveitar a Praia do Futuro\"," +
            "\"texto\":\"A Praia do Futuro é acessível e bem estruturada, com opções para todos os bolsos.\"," +
            "\"imagem\":\"/images/geral/Futuro.jpeg\"," +
            "\"subsecoes\":[{\"titulo\":\"Como Chegar\",\"texto\":\"De ônibus, pegue as linhas que passam pela Av. Zezé Diogo. De carro ou aplicativo, siga pela Av. Dioguinho. Há estacionamento nas barracas.\"}," +
            "{\"titulo\":\"Melhor Horário\",\"texto\":\"Pela manhã, o mar é mais calmo e a praia menos movimentada. À tarde, as barracas ficam mais animadas com música ao vivo.\"}]," +
            "\"recomendacoes\":[{\"titulo\":\"Barracas Imperdíveis\",\"itens\":[{\"nome\":\"Chico do Caranguejo\",\"nota\":4.8,\"contato\":\"(85) 3262-0022\",\"site\":\"https://www.chicodocara nguejo.com.br/\"},{\"nome\":\"Crocobeach\",\"nota\":4.7,\"contato\":\"(85) 3234-4444\",\"site\":\"https://www.crocobeach.com.br/\"}]}," +
            "{\"titulo\":\"Onde Ficar\",\"itens\":[{\"nome\":\"Hotel Praia Futuro\",\"nota\":4.5,\"contato\":\"(85) 3262-1000\",\"site\":\"https://www.instagram.com/hotelpraiafuturo/\"},{\"nome\":\"Pousada Sol e Mar\",\"nota\":4.6,\"contato\":\"(85) 3262-2000\",\"site\":\"https://www.instagram.com/\"}]}]}," +
            "\"fotos\":{\"label\":\"Fotos\"},\"avaliacoes\":{\"label\":\"Avaliações\"}}}");

        seedInfoLocal("/ceara/serra-de-baturite",
            "{\"carouselImages\":[\"/images/geral/serra.jpg\",\"/images/geral/serra.jpg\"]," +
            "\"galleryImages\":[{\"src\":\"/images/geral/serra.jpg\"},{\"src\":\"/images/geral/serra.jpg\"},{\"src\":\"/images/geral/serra.jpg\"}]," +
            "\"secoes\":{\"sobre\":{\"label\":\"Sobre\",\"titulo\":\"O Oásis Verde do Ceará\"," +
            "\"texto\":\"A Serra de Baturité é um verdadeiro oásis no semiárido cearense. Com altitude de até 1.114 metros, a região desfruta de um clima ameno e úmido, com temperaturas que raramente ultrapassam 25°C. A vegetação exuberante, as cachoeiras cristalinas e as plantações de café e banana criam uma paisagem completamente diferente do restante do estado.\"," +
            "\"imagem\":\"/images/geral/serra.jpg\"," +
            "\"lista\":[\"Localização: A 100 km de Fortaleza, na Região Maciço de Baturité.\",\"Altitude: Até 1.114 metros no Pico Alto.\",\"Clima: Ameno, entre 18°C e 25°C durante todo o ano.\"]}," +
            "\"experiencias\":{\"label\":\"Experiências\",\"titulo\":\"Natureza e Cultura na Serra\"," +
            "\"texto\":\"A Serra de Baturité oferece uma combinação única de ecoturismo, gastronomia e cultura caipira cearense.\"," +
            "\"imagem\":\"/images/geral/serra.jpg\"," +
            "\"subsecoes\":[{\"titulo\":\"Cachoeiras e Trilhas\",\"texto\":\"A região abriga diversas cachoeiras, como a Cachoeira do Pinga e a Cachoeira do Urubu. As trilhas ecológicas percorrem a Mata Atlântica serrana, com rica biodiversidade.\"}," +
            "{\"titulo\":\"Rota do Café\",\"texto\":\"Baturité é famosa pela produção de café de altitude. Fazendas históricas abrem suas portas para visitas guiadas, onde é possível conhecer todo o processo de produção e degustar o café local.\"}," +
            "{\"titulo\":\"Guaramiranga\",\"texto\":\"O município de Guaramiranga, na serra, é conhecido como a \\\"Suíça Cearense\\\" e sedia o famoso Festival de Jazz e Blues, um dos mais importantes do Nordeste.\"}]}," +
            "\"visita\":{\"label\":\"Visite\",\"titulo\":\"Como Visitar a Serra de Baturité\"," +
            "\"texto\":\"A Serra de Baturité é um destino de fim de semana perfeito para quem está em Fortaleza.\"," +
            "\"imagem\":\"/images/geral/serra.jpg\"," +
            "\"subsecoes\":[{\"titulo\":\"Como Chegar\",\"texto\":\"De Fortaleza, siga pela BR-222 até Pacatuba, depois pela CE-065 até Baturité (aprox. 1h30). Há ônibus regulares saindo da Rodoviária de Fortaleza.\"}," +
            "{\"titulo\":\"Melhor Época\",\"texto\":\"De fevereiro a maio, o período chuvoso deixa a vegetação mais verde e as cachoeiras mais cheias. De junho a janeiro, o clima é mais seco e agradável para trilhas.\"}]," +
            "\"recomendacoes\":[{\"titulo\":\"Onde Comer\",\"itens\":[{\"nome\":\"Restaurante Sítio das Flores\",\"nota\":4.8,\"contato\":\"(85) 3325-1234\",\"site\":\"https://www.instagram.com/\"},{\"nome\":\"Café da Serra\",\"nota\":4.7,\"contato\":\"(85) 3325-5678\",\"site\":\"https://www.instagram.com/\"}]}," +
            "{\"titulo\":\"Onde Ficar\",\"itens\":[{\"nome\":\"Pousada Recanto da Serra\",\"nota\":4.7,\"contato\":\"(85) 3325-1111\",\"site\":\"https://www.instagram.com/\"},{\"nome\":\"Hotel Fazenda Serra Verde\",\"nota\":4.6,\"contato\":\"(85) 3325-2222\",\"site\":\"https://www.instagram.com/\"}]}]}," +
            "\"fotos\":{\"label\":\"Fotos\"},\"avaliacoes\":{\"label\":\"Avaliações\"}}}");

        seedInfoLocal("/ceara/chapada-do-araripe",
            "{\"carouselImages\":[\"/images/geral/chapada.webp\",\"/images/geral/chapada.webp\"]," +
            "\"galleryImages\":[{\"src\":\"/images/geral/chapada.webp\"},{\"src\":\"/images/geral/chapada.webp\"},{\"src\":\"/images/geral/chapada.webp\"}]," +
            "\"secoes\":{\"sobre\":{\"label\":\"Sobre\",\"titulo\":\"O Tesouro Paleontológico do Brasil\"," +
            "\"texto\":\"A Chapada do Araripe é um planalto sedimentar que se estende pelos estados do Ceará, Pernambuco e Piauí. Com altitude média de 900 metros, a região abriga um dos mais importantes sítios paleontológicos do mundo, com fósseis de dinossauros e peixes pré-históricos. Além disso, a chapada é famosa pelas fontes de água cristalina que brotam naturalmente do solo.\"," +
            "\"imagem\":\"/images/geral/chapada.webp\"," +
            "\"lista\":[\"Localização: Crato e Juazeiro do Norte, sul do Ceará.\",\"Altitude: Média de 900 metros acima do nível do mar.\",\"Destaque: Geopark Araripe, primeiro geopark das Américas reconhecido pela UNESCO.\"]}," +
            "\"experiencias\":{\"label\":\"Experiências\",\"titulo\":\"Natureza e Ciência na Chapada\"," +
            "\"texto\":\"A Chapada do Araripe combina beleza natural, história geológica e espiritualidade em um destino único no Brasil.\"," +
            "\"imagem\":\"/images/geral/chapada.webp\"," +
            "\"subsecoes\":[{\"titulo\":\"Fontes e Balneários\",\"texto\":\"As fontes de água doce que brotam da chapada formam balneários naturais de beleza rara. A Fonte do Caldas, a Bica do Ipu e o Balneário do Caldas são os mais visitados.\"}," +
            "{\"titulo\":\"Geopark Araripe\",\"texto\":\"O Geopark Araripe reúne sítios geológicos e paleontológicos de importância mundial. O Museu de Paleontologia de Santana do Cariri exibe fósseis únicos encontrados na região.\"}," +
            "{\"titulo\":\"Juazeiro do Norte\",\"texto\":\"A cidade de Juazeiro do Norte, próxima à chapada, é um importante centro de peregrinação religiosa, com o Memorial Padre Cícero e a estátua do Padre Cícero, uma das maiores do Brasil.\"}]}," +
            "\"visita\":{\"label\":\"Visite\",\"titulo\":\"Como Visitar a Chapada do Araripe\"," +
            "\"texto\":\"A Chapada do Araripe é acessível a partir de Crato ou Juazeiro do Norte, no sul do Ceará.\"," +
            "\"imagem\":\"/images/geral/chapada.webp\"," +
            "\"subsecoes\":[{\"titulo\":\"Como Chegar\",\"texto\":\"De Fortaleza, há voos e ônibus para Juazeiro do Norte (aprox. 6h de ônibus). De carro, siga pela BR-116 Sul. O aeroporto de Juazeiro do Norte tem voos regulares.\"}," +
            "{\"titulo\":\"Melhor Época\",\"texto\":\"De maio a setembro, o período seco é ideal para trilhas e visitas aos sítios paleontológicos. De outubro a abril, as fontes ficam mais cheias e a vegetação mais exuberante.\"}]," +
            "\"recomendacoes\":[{\"titulo\":\"Onde Comer\",\"itens\":[{\"nome\":\"Restaurante Sabor do Cariri\",\"nota\":4.7,\"contato\":\"(88) 3521-1234\",\"site\":\"https://www.instagram.com/\"},{\"nome\":\"Casa Grande Restaurante\",\"nota\":4.6,\"contato\":\"(88) 3521-5678\",\"site\":\"https://www.instagram.com/\"}]}," +
            "{\"titulo\":\"Onde Ficar\",\"itens\":[{\"nome\":\"Hotel Panorama Juazeiro\",\"nota\":4.6,\"contato\":\"(88) 3512-1000\",\"site\":\"https://www.instagram.com/\"},{\"nome\":\"Pousada Chapada Verde\",\"nota\":4.7,\"contato\":\"(88) 3521-3333\",\"site\":\"https://www.instagram.com/\"}]}]}," +
            "\"fotos\":{\"label\":\"Fotos\"},\"avaliacoes\":{\"label\":\"Avaliações\"}}}");

        seedInfoLocal("/ceara/centro-historico-fortaleza",
            "{\"carouselImages\":[\"/images/geral/centro.jpeg\",\"/images/geral/centro.jpeg\"]," +
            "\"galleryImages\":[{\"src\":\"/images/geral/centro.jpeg\"},{\"src\":\"/images/geral/centro.jpeg\"},{\"src\":\"/images/geral/centro.jpeg\"}]," +
            "\"secoes\":{\"sobre\":{\"label\":\"Sobre\",\"titulo\":\"A Alma Histórica de Fortaleza\"," +
            "\"texto\":\"O Centro Histórico de Fortaleza é o berço da capital cearense. Com ruas que guardam séculos de história, o bairro concentra os principais monumentos, igrejas e mercados da cidade. Do imponente Theatro José de Alencar ao movimentado Mercado Central, cada esquina conta uma história da formação cultural e econômica do Ceará.\"," +
            "\"imagem\":\"/images/geral/centro.jpeg\"," +
            "\"lista\":[\"Localização: Centro de Fortaleza, capital do Ceará.\",\"Destaque: Theatro José de Alencar, obra do Art Nouveau brasileiro.\",\"Compras: Mercado Central, com artesanato e produtos típicos.\"]}," +
            "\"experiencias\":{\"label\":\"Experiências\",\"titulo\":\"O Que Explorar no Centro\"," +
            "\"texto\":\"O Centro Histórico de Fortaleza é um roteiro cultural completo, com monumentos, museus, gastronomia e artesanato.\"," +
            "\"imagem\":\"/images/geral/centro.jpeg\"," +
            "\"subsecoes\":[{\"titulo\":\"Theatro José de Alencar\",\"texto\":\"Inaugurado em 1910, o Theatro José de Alencar é uma obra-prima do Art Nouveau com elementos neoclássicos. Sua estrutura metálica importada da Escócia e os vitrais coloridos fazem dele um dos teatros mais belos do Brasil.\"}," +
            "{\"titulo\":\"Mercado Central\",\"texto\":\"Com mais de 600 boxes, o Mercado Central é o maior mercado de artesanato do Ceará. Renda, bordados, cerâmica, cachaça e castanha de caju são alguns dos produtos típicos encontrados lá.\"}," +
            "{\"titulo\":\"Catedral Metropolitana\",\"texto\":\"A Catedral de Fortaleza, dedicada a Nossa Senhora da Assunção, é um imponente templo neogótico que domina a Praça da Sé. Sua construção durou mais de 60 anos, sendo concluída em 1978.\"}]}," +
            "\"visita\":{\"label\":\"Visite\",\"titulo\":\"Como Explorar o Centro Histórico\"," +
            "\"texto\":\"O Centro Histórico de Fortaleza é melhor explorado a pé, com um roteiro que conecta os principais pontos turísticos.\"," +
            "\"imagem\":\"/images/geral/centro.jpeg\"," +
            "\"subsecoes\":[{\"titulo\":\"Roteiro Sugerido\",\"texto\":\"Comece pela Praça do Ferreira, o coração do centro. Visite o Theatro José de Alencar, a Catedral Metropolitana e o Mercado Central. Termine no Centro Dragão do Mar, na Praia de Iracema.\"}," +
            "{\"titulo\":\"Como Chegar\",\"texto\":\"O centro é acessível por todas as linhas de ônibus de Fortaleza. O metrô tem estações próximas (José de Alencar e Colégio Militar). De aplicativo, é fácil e rápido.\"}]," +
            "\"recomendacoes\":[{\"titulo\":\"Onde Comer\",\"itens\":[{\"nome\":\"Restaurante Colher de Pau\",\"nota\":4.8,\"contato\":\"(85) 3219-3773\",\"site\":\"https://www.colherdepau.com.br/\"},{\"nome\":\"Pastelaria Tropical\",\"nota\":4.5,\"contato\":\"(85) 3231-1234\",\"site\":\"https://www.instagram.com/\"}]}," +
            "{\"titulo\":\"Onde Ficar\",\"itens\":[{\"nome\":\"Hotel Luzeiros Fortaleza\",\"nota\":4.7,\"contato\":\"(85) 4006-8585\",\"site\":\"https://www.luzeiroshotel.com.br/\"},{\"nome\":\"Gran Marquise Hotel\",\"nota\":4.8,\"contato\":\"(85) 3466-5000\",\"site\":\"https://www.granmarquise.com.br/\"}]}]}," +
            "\"fotos\":{\"label\":\"Fotos\"},\"avaliacoes\":{\"label\":\"Avaliações\"}}}");

        // ── MINAS GERAIS ──
        seedInfoLocal("/mg/ouro-preto",
            "{\"carouselImages\":[\"/images/monumentos/ouro.jpeg\",\"/images/monumentos/independencia.webp\"]," +
            "\"galleryImages\":[{\"src\":\"/images/monumentos/ouro.jpeg\"},{\"src\":\"/images/monumentos/independencia.webp\"},{\"src\":\"/images/monumentos/pala.jpeg\"}]," +
            "\"secoes\":{\"sobre\":{\"label\":\"Sobre\",\"titulo\":\"A Capital do Ouro e da Inconfidência\"," +
            "\"texto\":\"Ouro Preto foi a capital de Minas Gerais durante o ciclo do ouro no século XVIII e o epicentro da Inconfidência Mineira. Tombada como Patrimônio Mundial da UNESCO em 1980, preserva mais de 20 igrejas barrocas, museus e casarões coloniais.\"," +
            "\"imagem\":\"/images/monumentos/ouro.jpeg\"," +
            "\"lista\":[\"UNESCO: Patrimônio Mundial desde 1980.\",\"Altitude: 1.179 metros acima do nível do mar.\",\"Destaque: Igreja de São Francisco de Assis, obra-prima de Aleijadinho.\"]}," +
            "\"experiencias\":{\"label\":\"Experiências\",\"titulo\":\"O Que Explorar em Ouro Preto\"," +
            "\"texto\":\"Ouro Preto é um museu a céu aberto. Cada rua de paralelepípedo, cada igreja e cada mirante conta uma história fascinante.\"," +
            "\"imagem\":\"/images/monumentos/independencia.webp\"," +
            "\"subsecoes\":[{\"titulo\":\"Igrejas Barrocas\",\"texto\":\"As 13 igrejas de Ouro Preto são obras-primas do barroco. A Igreja de São Francisco de Assis e a Igreja Nossa Senhora do Pilar, com 400 kg de ouro, são as mais impressionantes.\"}," +
            "{\"titulo\":\"Museu da Inconfidência\",\"texto\":\"Instalado no antigo Palácio Municipal, preserva documentos e a história da Inconfidência Mineira de 1789.\"}," +
            "{\"titulo\":\"Minas de Ouro\",\"texto\":\"A Mina do Chico Rei e a Mina da Passagem oferecem visitas guiadas ao interior das minas do século XVIII.\"}]}," +
            "\"visita\":{\"label\":\"Visite\",\"titulo\":\"Como Visitar Ouro Preto\",\"texto\":\"\"," +
            "\"imagem\":\"/images/monumentos/pala.jpeg\"," +
            "\"subsecoes\":[{\"titulo\":\"Como Chegar\",\"texto\":\"De Belo Horizonte, há ônibus regulares (2h) e excursões diárias. De carro, siga pela BR-356.\"}," +
            "{\"titulo\":\"Melhor Época\",\"texto\":\"O Carnaval e a Semana Santa são momentos especiais. O inverno (junho-agosto) tem clima ameno.\"}]," +
            "\"recomendacoes\":[{\"titulo\":\"Onde Comer\",\"itens\":[{\"nome\":\"Restaurante Chafariz\",\"nota\":4.8,\"contato\":\"(31) 3551-2828\",\"site\":\"https://www.instagram.com/\"},{\"nome\":\"Casa do Ouvidor\",\"nota\":4.7,\"contato\":\"(31) 3551-3141\",\"site\":\"https://www.instagram.com/\"}]}," +
            "{\"titulo\":\"Onde Ficar\",\"itens\":[{\"nome\":\"Pousada do Mondego\",\"nota\":4.8,\"contato\":\"(31) 3551-2040\",\"site\":\"https://www.pousadadomondego.com.br/\"},{\"nome\":\"Hotel Solar do Rosário\",\"nota\":4.7,\"contato\":\"(31) 3551-5200\",\"site\":\"https://www.instagram.com/\"}]}]}," +
            "\"fotos\":{\"label\":\"Fotos\"},\"avaliacoes\":{\"label\":\"Avaliações\"}}}");

        seedInfoLocal("/mg/inhotim",
            "{\"carouselImages\":[\"/images/monumentos/independencia.webp\",\"/images/natureza/chapada.jpeg\"]," +
            "\"galleryImages\":[{\"src\":\"/images/monumentos/independencia.webp\"},{\"src\":\"/images/natureza/chapada.jpeg\"},{\"src\":\"/images/monumentos/ouro.jpeg\"}]," +
            "\"secoes\":{\"sobre\":{\"label\":\"Sobre\",\"titulo\":\"Arte e Natureza em Perfeita Harmonia\"," +
            "\"texto\":\"O Instituto Inhotim é um museu de arte contemporânea integrado a um jardim botânico de 140 hectares. Fundado em 2006, reúne obras de mais de 200 artistas de 40 países.\"," +
            "\"imagem\":\"/images/monumentos/independencia.webp\"," +
            "\"lista\":[\"Área: 140 hectares de jardins e galerias.\",\"Acervo: Obras de mais de 200 artistas de 40 países.\",\"Jardim Botânico: Mais de 4.500 espécies de plantas.\"]}," +
            "\"experiencias\":{\"label\":\"Experiências\",\"titulo\":\"Uma Jornada pela Arte Contemporânea\"," +
            "\"texto\":\"Inhotim oferece uma experiência imersiva onde arte, natureza e arquitetura se fundem.\"," +
            "\"imagem\":\"/images/natureza/chapada.jpeg\"," +
            "\"subsecoes\":[{\"titulo\":\"Galerias Permanentes\",\"texto\":\"As galerias abrigam instalações de Cildo Meireles, Tunga e outros artistas que desafiam a percepção do espaço.\"}," +
            "{\"titulo\":\"Jardim Botânico\",\"texto\":\"Um dos mais importantes do Brasil, com coleções de palmeiras, bromélias, orquídeas e plantas tropicais raras.\"}," +
            "{\"titulo\":\"Exposições Temporárias\",\"texto\":\"O Inhotim recebe exposições temporárias de artistas nacionais e internacionais ao longo do ano.\"}]}," +
            "\"visita\":{\"label\":\"Visite\",\"titulo\":\"Como Visitar o Inhotim\",\"texto\":\"\"," +
            "\"imagem\":\"/images/monumentos/ouro.jpeg\"," +
            "\"subsecoes\":[{\"titulo\":\"Horários e Ingressos\",\"texto\":\"Quarta a domingo, das 9h30 às 17h30. Ingressos a partir de R$ 50. Às quartas, entrada gratuita para moradores de Brumadinho.\"}," +
            "{\"titulo\":\"Como Chegar\",\"texto\":\"De BH, há ônibus direto da Rodoviária (1h30). De carro, siga pela BR-381 até Brumadinho.\"}]," +
            "\"recomendacoes\":[{\"titulo\":\"Onde Comer no Inhotim\",\"itens\":[{\"nome\":\"Restaurante Tamboril\",\"nota\":4.8,\"contato\":\"(31) 3571-9700\",\"site\":\"https://www.inhotim.org.br/\"},{\"nome\":\"Café das Artes\",\"nota\":4.6,\"contato\":\"(31) 3571-9700\",\"site\":\"https://www.inhotim.org.br/\"}]}," +
            "{\"titulo\":\"Onde Ficar\",\"itens\":[{\"nome\":\"Pousada Inhotim\",\"nota\":4.9,\"contato\":\"(31) 3571-9700\",\"site\":\"https://www.inhotim.org.br/\"},{\"nome\":\"Hotel Fazenda Brumadinho\",\"nota\":4.6,\"contato\":\"(31) 3571-1234\",\"site\":\"https://www.instagram.com/\"}]}]}," +
            "\"fotos\":{\"label\":\"Fotos\"},\"avaliacoes\":{\"label\":\"Avaliações\"}}}");

        // ── ESPÍRITO SANTO ──
        seedInfoLocal("/es/pedra-azul",
            "{\"carouselImages\":[\"/images/natureza/veadeiros.jpeg\",\"/images/natureza/bonito.jpeg\"]," +
            "\"galleryImages\":[{\"src\":\"/images/natureza/veadeiros.jpeg\"},{\"src\":\"/images/natureza/bonito.jpeg\"},{\"src\":\"/images/natureza/chapada.jpeg\"}]," +
            "\"secoes\":{\"sobre\":{\"label\":\"Sobre\",\"titulo\":\"A Pedra que Muda de Cor\"," +
            "\"texto\":\"A Pedra Azul é uma formação rochosa de granito com 1.822 metros de altitude em Domingos Martins. Seu nome vem da coloração azulada que adquire ao amanhecer e ao entardecer.\"," +
            "\"imagem\":\"/images/natureza/veadeiros.jpeg\"," +
            "\"lista\":[\"Altitude: 1.822 metros acima do nível do mar.\",\"Localização: Domingos Martins, a 90 km de Vitória.\",\"Destaque: Coloração azulada ao amanhecer e entardecer.\"]}," +
            "\"experiencias\":{\"label\":\"Experiências\",\"titulo\":\"Aventura na Serra Capixaba\"," +
            "\"texto\":\"O Parque Estadual da Pedra Azul oferece trilhas, piscinas naturais e vistas panorâmicas.\"," +
            "\"imagem\":\"/images/natureza/bonito.jpeg\"," +
            "\"subsecoes\":[{\"titulo\":\"Trilha da Pedra Azul\",\"texto\":\"A trilha principal percorre 2,5 km até o mirante da Pedra Azul, com desnível de 400 metros. O percurso passa por Mata Atlântica preservada, piscinas naturais e formações rochosas únicas.\"}," +
            "{\"titulo\":\"Piscinas Naturais\",\"texto\":\"Ao longo da trilha, piscinas naturais de água cristalina formadas por nascentes da serra convidam para um mergulho refrescante em meio à natureza.\"}," +
            "{\"titulo\":\"Observação de Aves\",\"texto\":\"O parque é um paraíso para observadores de aves, com mais de 200 espécies registradas, incluindo o beija-flor-de-fronte-violeta e o tucano-de-bico-verde.\"}]}," +
            "\"visita\":{\"label\":\"Visite\",\"titulo\":\"Como Visitar a Pedra Azul\"," +
            "\"texto\":\"O Parque Estadual da Pedra Azul fica em Domingos Martins, a 90 km de Vitória, com fácil acesso pela BR-262.\"," +
            "\"imagem\":\"/images/natureza/chapada.jpeg\"," +
            "\"subsecoes\":[{\"titulo\":\"Horários e Ingressos\",\"texto\":\"O parque funciona de terça a domingo, das 8h às 17h. Ingresso: R$ 20 (adulto) e R$ 10 (meia). A trilha principal requer agendamento prévio e guia credenciado.\"}," +
            "{\"titulo\":\"Como Chegar\",\"texto\":\"De Vitória, siga pela BR-262 em direção a Belo Horizonte até Domingos Martins (90 km). O parque fica a 15 km do centro da cidade. Há ônibus regulares de Vitória para Domingos Martins.\"}]," +
            "\"recomendacoes\":[{\"titulo\":\"Onde Comer\",\"itens\":[{\"nome\":\"Restaurante Recanto da Pedra\",\"nota\":4.8,\"contato\":\"(27) 3268-1234\",\"site\":\"https://www.instagram.com/\"},{\"nome\":\"Café da Serra\",\"nota\":4.7,\"contato\":\"(27) 3268-5678\",\"site\":\"https://www.instagram.com/\"}]}," +
            "{\"titulo\":\"Onde Ficar\",\"itens\":[{\"nome\":\"Pousada Pedra Azul\",\"nota\":4.8,\"contato\":\"(27) 3248-1234\",\"site\":\"https://www.instagram.com/\"},{\"nome\":\"Hotel Serra Verde\",\"nota\":4.6,\"contato\":\"(27) 3248-5678\",\"site\":\"https://www.instagram.com/\"}]}]}," +
            "\"fotos\":{\"label\":\"Fotos\"},\"avaliacoes\":{\"label\":\"Avaliações\"}}}");

        seedInfoLocal("/es/guarapari",
            "{\"carouselImages\":[\"/images/geral/praiaEx.jpg\",\"/images/natureza/bonito.jpeg\"]," +
            "\"galleryImages\":[{\"src\":\"/images/geral/praiaEx.jpg\"},{\"src\":\"/images/natureza/bonito.jpeg\"},{\"src\":\"/images/natureza/veadeiros.jpeg\"}]," +
            "\"secoes\":{\"sobre\":{\"label\":\"Sobre\",\"titulo\":\"As Areias que Curam\"," +
            "\"texto\":\"Guarapari é conhecida como a Cidade Saúde por suas areias monazíticas com propriedades terapêuticas. Com mais de 23 praias e águas mornas, recebe mais de 1 milhão de turistas por ano.\"," +
            "\"imagem\":\"/images/geral/praiaEx.jpg\"," +
            "\"lista\":[\"Praias: Mais de 23 praias ao longo do litoral.\",\"Destaque: Areias monazíticas com propriedades terapêuticas.\",\"Temperatura: Águas mornas entre 24°C e 28°C.\"]}," +
            "\"experiencias\":{\"label\":\"Experiências\",\"titulo\":\"Sol, Mar e Saúde em Guarapari\"," +
            "\"texto\":\"Guarapari tem infraestrutura turística completa para todos os perfis de visitantes.\"," +
            "\"imagem\":\"/images/natureza/bonito.jpeg\"," +
            "\"subsecoes\":[{\"titulo\":\"Praias Principais\",\"texto\":\"A Praia do Morro, com 4 km de extensão, é a mais movimentada. A Praia de Meaípe é famosa pelos frutos do mar frescos. A Praia das Castanheiras é ideal para famílias com crianças.\"}," +
            "{\"titulo\":\"Mergulho e Esportes\",\"texto\":\"As águas claras de Guarapari são perfeitas para mergulho, com visibilidade de até 15 metros. Passeios de barco, stand-up paddle e kitesurf também são populares na região.\"}," +
            "{\"titulo\":\"Gastronomia\",\"texto\":\"Os frutos do mar de Guarapari são famosos em todo o Brasil. Caranguejos, lagostas, camarões e peixes frescos são servidos nas barracas e restaurantes da orla.\"}]}," +
            "\"visita\":{\"label\":\"Visite\",\"titulo\":\"Como Chegar a Guarapari\"," +
            "\"texto\":\"Guarapari fica a 53 km de Vitória e é facilmente acessível de carro ou ônibus.\"," +
            "\"imagem\":\"/images/natureza/veadeiros.jpeg\"," +
            "\"subsecoes\":[{\"titulo\":\"Como Chegar\",\"texto\":\"De Vitória, siga pela BR-101 Sul até Guarapari (53 km, 45 min). Há ônibus regulares saindo do Terminal de Vitória. O aeroporto mais próximo é o de Vitória (Eurico de Aguiar Salles).\"}," +
            "{\"titulo\":\"Melhor Época\",\"texto\":\"De dezembro a março, na alta temporada, as praias ficam mais movimentadas. De abril a novembro, o clima é mais ameno e as praias menos lotadas, com preços mais acessíveis.\"}]," +
            "\"recomendacoes\":[{\"titulo\":\"Onde Comer\",\"itens\":[{\"nome\":\"Restaurante Meaípe\",\"nota\":4.8,\"contato\":\"(27) 3272-1234\",\"site\":\"https://www.instagram.com/\"},{\"nome\":\"Barraca do Zé\",\"nota\":4.7,\"contato\":\"(27) 3272-5678\",\"site\":\"https://www.instagram.com/\"}]}," +
            "{\"titulo\":\"Onde Ficar\",\"itens\":[{\"nome\":\"Hotel Praia do Morro\",\"nota\":4.6,\"contato\":\"(27) 3261-1000\",\"site\":\"https://www.instagram.com/\"},{\"nome\":\"Pousada Sol e Mar\",\"nota\":4.7,\"contato\":\"(27) 3261-5678\",\"site\":\"https://www.instagram.com/\"}]}]}," +
            "\"fotos\":{\"label\":\"Fotos\"},\"avaliacoes\":{\"label\":\"Avaliações\"}}}");

        // ── NORTE ──
        seedInfoLocal("/acre/parque-chandless",
            "{\"carouselImages\":[\"/images/geral/amazonas1.avif\",\"/images/geral/amazonas2.jpg\"]," +
            "\"galleryImages\":[{\"src\":\"/images/geral/amazonas1.avif\"},{\"src\":\"/images/geral/amazonas2.jpg\"},{\"src\":\"/images/geral/amazonas3.1.jpg\"}]," +
            "\"secoes\":{\"sobre\":{\"label\":\"Sobre\",\"titulo\":\"O Último Paraíso Intocado do Acre\"," +
            "\"texto\":\"O Parque Estadual Chandless tem mais de 695.000 hectares de floresta amazônica primária na fronteira com o Peru. Um dos ecossistemas mais preservados do planeta.\"," +
            "\"imagem\":\"/images/geral/amazonas1.avif\"," +
            "\"lista\":[\"Área: 695.303 hectares de floresta primária.\",\"Localização: Santa Rosa do Purus, fronteira com o Peru.\",\"Destaque: Um dos parques mais remotos e preservados do Brasil.\"]}," +
            "\"experiencias\":{\"label\":\"Experiências\",\"titulo\":\"Aventura na Floresta Primária\"," +
            "\"texto\":\"Ecoturismo em uma das florestas mais remotas do Brasil.\"," +
            "\"imagem\":\"/images/geral/amazonas2.jpg\"," +
            "\"subsecoes\":[{\"titulo\":\"Observação de Fauna\",\"texto\":\"O parque abriga onças-pintadas, antas, queixadas, ariranhas e centenas de espécies de aves. A observação de fauna é uma das principais atrações para pesquisadores e ecoturistas.\"}," +
            "{\"titulo\":\"Passeios de Barco\",\"texto\":\"Os rios Chandless e Purus são as principais vias de acesso ao parque. Passeios de barco permitem explorar a floresta de igapó e observar a vida selvagem às margens dos rios.\"}," +
            "{\"titulo\":\"Contato com Povos Indígenas\",\"texto\":\"O parque faz fronteira com territórios de povos indígenas. Visitas guiadas e responsáveis a comunidades indígenas de contato são organizadas por operadoras especializadas.\"}]}," +
            "\"visita\":{\"label\":\"Visite\",\"titulo\":\"Como Visitar o Parque Chandless\"," +
            "\"texto\":\"O acesso ao Parque Chandless é feito por via fluvial a partir de Rio Branco, com apoio de operadoras de ecoturismo especializadas.\"," +
            "\"imagem\":\"/images/geral/amazonas3.1.jpg\"," +
            "\"subsecoes\":[{\"titulo\":\"Como Chegar\",\"texto\":\"De Rio Branco, voos fretados ou barcos pelo Rio Purus chegam a Santa Rosa do Purus. De lá, barcos regionais acessam o parque. O trajeto pode levar de 2 a 5 dias por via fluvial.\"}," +
            "{\"titulo\":\"Melhor Época\",\"texto\":\"De junho a outubro, na estação seca, os rios ficam mais baixos e as praias fluviais aparecem. De novembro a maio, as chuvas tornam a floresta mais exuberante.\"}]," +
            "\"recomendacoes\":[{\"titulo\":\"Operadoras de Ecoturismo\",\"itens\":[{\"nome\":\"Acre Ecoturismo\",\"nota\":4.8,\"contato\":\"(68) 9999-1234\",\"site\":\"https://www.instagram.com/\"},{\"nome\":\"Amazônia Selvagem Tours\",\"nota\":4.7,\"contato\":\"(68) 9888-5678\",\"site\":\"https://www.instagram.com/\"}]}," +
            "{\"titulo\":\"Onde Ficar em Rio Branco\",\"itens\":[{\"nome\":\"Hotel Inácio Palace\",\"nota\":4.6,\"contato\":\"(68) 3224-6300\",\"site\":\"https://www.instagram.com/\"},{\"nome\":\"Pousada Ecológica Acre\",\"nota\":4.7,\"contato\":\"(68) 9777-9012\",\"site\":\"https://www.instagram.com/\"}]}]}," +
            "\"fotos\":{\"label\":\"Fotos\"},\"avaliacoes\":{\"label\":\"Avaliações\"}}}");

        seedInfoLocal("/acre/centro-historico",
            "{\"carouselImages\":[\"/images/geral/amazonas2.jpg\",\"/images/geral/amazonas1.avif\"]," +
            "\"galleryImages\":[{\"src\":\"/images/geral/amazonas2.jpg\"},{\"src\":\"/images/geral/amazonas1.avif\"},{\"src\":\"/images/geral/oam.jpg\"}]," +
            "\"secoes\":{\"sobre\":{\"label\":\"Sobre\",\"titulo\":\"A Alma Histórica de Rio Branco\"," +
            "\"texto\":\"O Centro Histórico de Rio Branco concentra o Palácio Rio Branco, o Museu da Borracha e a Casa dos Povos da Floresta, contando a história dos seringueiros e povos indígenas.\"," +
            "\"imagem\":\"/images/geral/amazonas2.jpg\"," +
            "\"lista\":[\"Destaque: Palácio Rio Branco, sede do governo estadual.\",\"Museu: Museu da Borracha, com acervo sobre o ciclo da borracha.\",\"Cultura: Casa dos Povos da Floresta, com artesanato indígena.\"]}," +
            "\"experiencias\":{\"label\":\"Experiências\",\"titulo\":\"O Que Explorar no Centro\"," +
            "\"texto\":\"Roteiro cultural completo com monumentos, museus e gastronomia típica acreana.\"," +
            "\"imagem\":\"/images/geral/amazonas1.avif\"," +
            "\"subsecoes\":[{\"titulo\":\"Museu da Borracha\",\"texto\":\"O museu preserva a memória do ciclo da borracha, que transformou a Amazônia no final do século XIX. Com acervo de ferramentas, fotografias e documentos históricos, é uma visita obrigatória.\"}," +
            "{\"titulo\":\"Palácio Rio Branco\",\"texto\":\"A sede do governo estadual é um dos edifícios mais imponentes de Rio Branco, com arquitetura neoclássica e jardins bem cuidados. Visitas guiadas são realizadas em dias úteis.\"}," +
            "{\"titulo\":\"Calçadão da Gameleira\",\"texto\":\"O calçadão às margens do Rio Acre é o coração da vida social de Rio Branco, com feiras de artesanato, apresentações culturais e um pôr do sol deslumbrante sobre o rio.\"}]}," +
            "\"visita\":{\"label\":\"Visite\",\"titulo\":\"Como Explorar Rio Branco\"," +
            "\"texto\":\"Rio Branco é uma cidade compacta e de fácil locomoção, com o centro histórico acessível a pé ou de táxi.\"," +
            "\"imagem\":\"/images/geral/oam.jpg\"," +
            "\"subsecoes\":[{\"titulo\":\"Como Chegar\",\"texto\":\"Rio Branco tem voos diretos de São Paulo, Brasília e Manaus. O Aeroporto Internacional Plácido de Castro fica a 22 km do centro. De ônibus, há linhas de várias capitais.\"}," +
            "{\"titulo\":\"Melhor Época\",\"texto\":\"De junho a outubro, na estação seca, o clima é mais ameno e as estradas estão em melhores condições. De novembro a maio, as chuvas intensas podem dificultar o acesso a algumas atrações.\"}]," +
            "\"recomendacoes\":[{\"titulo\":\"Onde Comer\",\"itens\":[{\"nome\":\"Restaurante Casarão\",\"nota\":4.7,\"contato\":\"(68) 3224-1234\",\"site\":\"https://www.instagram.com/\"},{\"nome\":\"Churrascaria do Acre\",\"nota\":4.6,\"contato\":\"(68) 3225-5678\",\"site\":\"https://www.instagram.com/\"}]}," +
            "{\"titulo\":\"Onde Ficar\",\"itens\":[{\"nome\":\"Hotel Inácio Palace\",\"nota\":4.6,\"contato\":\"(68) 3224-6300\",\"site\":\"https://www.instagram.com/\"},{\"nome\":\"Hotel Triângulo\",\"nota\":4.5,\"contato\":\"(68) 3223-1000\",\"site\":\"https://www.instagram.com/\"}]}]}," +
            "\"fotos\":{\"label\":\"Fotos\"},\"avaliacoes\":{\"label\":\"Avaliações\"}}}");

        seedInfoLocal("/amapa/fortaleza-sao-jose",
            "{\"carouselImages\":[\"/images/geral/amazonas1.avif\",\"/images/geral/amazonas3.1.jpg\"]," +
            "\"galleryImages\":[{\"src\":\"/images/geral/amazonas1.avif\"},{\"src\":\"/images/geral/amazonas3.1.jpg\"},{\"src\":\"/images/geral/oam.jpg\"}]," +
            "\"secoes\":{\"sobre\":{\"label\":\"Sobre\",\"titulo\":\"Guardiã da Fronteira Norte do Brasil\"," +
            "\"texto\":\"A Fortaleza de São José de Macapá foi construída entre 1764 e 1782 para defender a fronteira norte do Brasil. Com planta em estrela de quatro pontas, é a maior fortaleza da Amazônia brasileira.\"," +
            "\"imagem\":\"/images/geral/amazonas1.avif\"," +
            "\"lista\":[\"Construção: Entre 1764 e 1782, período colonial português.\",\"Arquitetura: Planta estrelada com quatro baluartes.\",\"Localização: Às margens do Rio Amazonas, em Macapá.\"]}," +
            "\"experiencias\":{\"label\":\"Experiências\",\"titulo\":\"Mergulhe na História Colonial\"," +
            "\"texto\":\"Experiência única de imersão na história colonial da Amazônia.\"," +
            "\"imagem\":\"/images/geral/amazonas3.1.jpg\"," +
            "\"subsecoes\":[{\"titulo\":\"Visita Guiada\",\"texto\":\"Guias especializados conduzem os visitantes pelos baluartes, casamatas e o pátio central da fortaleza, contando a história da construção e das batalhas travadas para defender a fronteira norte do Brasil.\"}," +
            "{\"titulo\":\"Vista do Rio Amazonas\",\"texto\":\"Da muralha da fortaleza, é possível contemplar uma vista deslumbrante do Rio Amazonas e da ilha de Santana. Ao entardecer, o pôr do sol sobre o rio é um espetáculo inesquecível.\"}," +
            "{\"titulo\":\"Eventos Culturais\",\"texto\":\"A fortaleza é palco de shows, festivais e eventos culturais ao longo do ano, especialmente durante o Festival do Marabaixo e as festas juninas de Macapá.\"}]}," +
            "\"visita\":{\"label\":\"Visite\",\"titulo\":\"Como Visitar a Fortaleza\"," +
            "\"texto\":\"A Fortaleza de São José de Macapá está localizada no centro histórico da capital, de fácil acesso a pé ou de táxi.\"," +
            "\"imagem\":\"/images/geral/oam.jpg\"," +
            "\"subsecoes\":[{\"titulo\":\"Horários e Ingressos\",\"texto\":\"A fortaleza funciona de terça a domingo, das 9h às 18h. A entrada é gratuita. Visitas guiadas são realizadas às 10h e às 15h, com duração de aproximadamente 1 hora.\"}," +
            "{\"titulo\":\"Como Chegar\",\"texto\":\"Localizada na Rua Cândido Mendes, no centro de Macapá. Acessível a pé do Marco Zero do Equador (500m) ou de táxi e aplicativos de transporte.\"}]," +
            "\"recomendacoes\":[{\"titulo\":\"Onde Comer por Perto\",\"itens\":[{\"nome\":\"Restaurante Cantina Italiana\",\"nota\":4.7,\"contato\":\"(96) 3222-1234\",\"site\":\"https://www.instagram.com/\"},{\"nome\":\"Peixaria do Amazonas\",\"nota\":4.8,\"contato\":\"(96) 3223-5678\",\"site\":\"https://www.instagram.com/\"}]}," +
            "{\"titulo\":\"Onde Ficar\",\"itens\":[{\"nome\":\"Hotel Novotel Macapá\",\"nota\":4.6,\"contato\":\"(96) 3198-3000\",\"site\":\"https://www.instagram.com/\"},{\"nome\":\"Pousada Ekinox\",\"nota\":4.7,\"contato\":\"(96) 9999-1234\",\"site\":\"https://www.instagram.com/\"}]}]}," +
            "\"fotos\":{\"label\":\"Fotos\"},\"avaliacoes\":{\"label\":\"Avaliações\"}}}");

        seedInfoLocal("/rondonia/ferrovia-madeira-mamore",
            "{\"carouselImages\":[\"/images/geral/amazonas2.jpg\",\"/images/geral/oam.jpg\"]," +
            "\"galleryImages\":[{\"src\":\"/images/geral/amazonas2.jpg\"},{\"src\":\"/images/geral/oam.jpg\"},{\"src\":\"/images/geral/amazonas3.1.jpg\"}]," +
            "\"secoes\":{\"sobre\":{\"label\":\"Sobre\",\"titulo\":\"A Ferrovia que Custou Vidas\"," +
            "\"texto\":\"A Estrada de Ferro Madeira-Mamoré, a Ferrovia do Diabo, foi construída entre 1907 e 1912 para escoar a borracha boliviana. Com 364 km, custou mais de 6.000 vidas. O Museu Ferroviário de Porto Velho preserva sua memória.\"," +
            "\"imagem\":\"/images/geral/amazonas2.jpg\"," +
            "\"lista\":[\"Construção: Entre 1907 e 1912.\",\"Extensão: 364 km entre Porto Velho e Guajará-Mirim.\",\"Trabalhadores: Mais de 6.000 mortes durante a construção.\"]}," +
            "\"experiencias\":{\"label\":\"Experiências\",\"titulo\":\"Uma Viagem no Tempo\"," +
            "\"texto\":\"O Museu Ferroviário oferece imersão na história da Ferrovia do Diabo.\"," +
            "\"imagem\":\"/images/geral/oam.jpg\"," +
            "\"subsecoes\":[{\"titulo\":\"Museu Ferroviário\",\"texto\":\"O museu preserva locomotivas originais, vagões, ferramentas e documentos históricos da ferrovia. A visita guiada conta a história dos trabalhadores que construíram a linha em condições extremas.\"}," +
            "{\"titulo\":\"Passeio de Trem\",\"texto\":\"Em datas especiais, é possível fazer um passeio de trem em locomotivas restauradas, percorrendo um trecho histórico da ferrovia às margens do Rio Madeira.\"}," +
            "{\"titulo\":\"Orla do Rio Madeira\",\"texto\":\"O museu fica às margens do Rio Madeira, na orla de Porto Velho. Após a visita, aproveite para passear pela orla e apreciar o pôr do sol sobre o maior afluente do Rio Amazonas.\"}]}," +
            "\"visita\":{\"label\":\"Visite\",\"titulo\":\"Como Visitar o Museu\"," +
            "\"texto\":\"O Museu Ferroviário de Porto Velho está localizado na orla do Rio Madeira, no centro da capital rondoniense.\"," +
            "\"imagem\":\"/images/geral/amazonas3.1.jpg\"," +
            "\"subsecoes\":[{\"titulo\":\"Horários e Ingressos\",\"texto\":\"O museu funciona de terça a domingo, das 8h às 17h. A entrada é gratuita. Visitas guiadas são realizadas mediante agendamento prévio.\"}," +
            "{\"titulo\":\"Como Chegar\",\"texto\":\"Localizado na Av. Farquar, às margens do Rio Madeira, no centro de Porto Velho. Acessível de táxi, aplicativos de transporte ou a pé do centro da cidade.\"}]," +
            "\"recomendacoes\":[{\"titulo\":\"Onde Comer\",\"itens\":[{\"nome\":\"Restaurante Miako\",\"nota\":4.7,\"contato\":\"(69) 3224-1234\",\"site\":\"https://www.instagram.com/\"},{\"nome\":\"Peixaria do Madeira\",\"nota\":4.8,\"contato\":\"(69) 3225-5678\",\"site\":\"https://www.instagram.com/\"}]}," +
            "{\"titulo\":\"Onde Ficar\",\"itens\":[{\"nome\":\"Hotel Vila Rica Porto Velho\",\"nota\":4.6,\"contato\":\"(69) 3216-3000\",\"site\":\"https://www.instagram.com/\"},{\"nome\":\"Pousada Rio Madeira\",\"nota\":4.5,\"contato\":\"(69) 9999-1234\",\"site\":\"https://www.instagram.com/\"}]}]}," +
            "\"fotos\":{\"label\":\"Fotos\"},\"avaliacoes\":{\"label\":\"Avaliações\"}}}");

        seedInfoLocal("/roraima/monte-roraima",
            "{\"carouselImages\":[\"/images/geral/oam.jpg\",\"/images/geral/amazonas1.avif\"]," +
            "\"galleryImages\":[{\"src\":\"/images/geral/oam.jpg\"},{\"src\":\"/images/geral/amazonas1.avif\"},{\"src\":\"/images/geral/amazonas2.jpg\"}]," +
            "\"secoes\":{\"sobre\":{\"label\":\"Sobre\",\"titulo\":\"O Ponto Mais Alto do Brasil\"," +
            "\"texto\":\"O Monte Roraima, com 2.875 metros, é o ponto mais alto do Brasil e uma das formações geológicas mais antigas do planeta (1,8 bilhão de anos). Localizado na tríplice fronteira Brasil-Venezuela-Guiana.\"," +
            "\"imagem\":\"/images/geral/oam.jpg\"," +
            "\"lista\":[\"Altitude: 2.875 metros — ponto mais alto do Brasil.\",\"Idade: Mais de 1,8 bilhão de anos.\",\"Fronteira: Brasil, Venezuela e Guiana.\"]}," +
            "\"experiencias\":{\"label\":\"Experiências\",\"titulo\":\"O Trekking Mais Épico do Brasil\"," +
            "\"texto\":\"Considerado uma das aventuras mais épicas da América do Sul.\"," +
            "\"imagem\":\"/images/geral/amazonas1.avif\"," +
            "\"subsecoes\":[{\"titulo\":\"Trekking ao Topo\",\"texto\":\"O trekking completo dura de 8 a 12 dias, partindo da aldeia Paraitepui. A trilha percorre savanas, florestas e tepuis, com acampamentos ao longo do caminho. O topo plano do Roraima é coberto por plantas carnívoras, cristais de quartzo e piscinas naturais.\"}," +
            "{\"titulo\":\"Flora e Fauna Únicas\",\"texto\":\"O topo do Roraima abriga espécies endêmicas que não existem em nenhum outro lugar do planeta, como a bromélia Brocchinia reducta e o sapo Oreophrynella quelchii. A neblina constante cria um ambiente místico e surreal.\"}," +
            "{\"titulo\":\"Tríplice Fronteira\",\"texto\":\"Do topo do Roraima, é possível ver os três países simultaneamente: Brasil, Venezuela e Guiana. Um marco de pedra indica o ponto exato da tríplice fronteira, uma experiência única no mundo.\"}]}," +
            "\"visita\":{\"label\":\"Visite\",\"titulo\":\"Como Fazer o Trekking\"," +
            "\"texto\":\"O trekking ao Monte Roraima exige planejamento, boa condição física e guia indígena obrigatório.\"," +
            "\"imagem\":\"/images/geral/amazonas2.jpg\"," +
            "\"subsecoes\":[{\"titulo\":\"Como Chegar\",\"texto\":\"De Boa Vista, pegue um ônibus ou carro até Pacaraima (215 km). De lá, siga até a aldeia Paraitepui (80 km de estrada de terra). A contratação de guia indígena é obrigatória e pode ser feita na aldeia.\"}," +
            "{\"titulo\":\"Melhor Época\",\"texto\":\"De dezembro a abril, na estação chuvosa, as cachoeiras estão mais cheias e a vegetação mais verde. De maio a novembro, na seca, as trilhas são mais fáceis e o céu mais limpo.\"}]," +
            "\"recomendacoes\":[{\"titulo\":\"Agências de Trekking\",\"itens\":[{\"nome\":\"Roraima Adventures\",\"nota\":4.9,\"contato\":\"(95) 9999-1234\",\"site\":\"https://www.instagram.com/\"},{\"nome\":\"Tepui Expedições\",\"nota\":4.8,\"contato\":\"(95) 9888-5678\",\"site\":\"https://www.instagram.com/\"}]}," +
            "{\"titulo\":\"Onde Ficar em Boa Vista\",\"itens\":[{\"nome\":\"Hotel Ekinox\",\"nota\":4.7,\"contato\":\"(95) 3623-1234\",\"site\":\"https://www.instagram.com/\"},{\"nome\":\"Pousada Roraima\",\"nota\":4.6,\"contato\":\"(95) 9777-9012\",\"site\":\"https://www.instagram.com/\"}]}]}," +
            "\"fotos\":{\"label\":\"Fotos\"},\"avaliacoes\":{\"label\":\"Avaliações\"}}}");

        seedInfoLocal("/tocantins/jalapao",
            "{\"carouselImages\":[\"/images/geral/amazonas3.1.jpg\",\"/images/geral/oam.jpg\"]," +
            "\"galleryImages\":[{\"src\":\"/images/geral/amazonas3.1.jpg\"},{\"src\":\"/images/geral/oam.jpg\"},{\"src\":\"/images/geral/amazonas1.avif\"}]," +
            "\"secoes\":{\"sobre\":{\"label\":\"Sobre\",\"titulo\":\"O Deserto Dourado do Cerrado\"," +
            "\"texto\":\"O Parque Estadual do Jalapão tem dunas de areia dourada de até 40 metros, fervedouros de água cristalina e cachoeiras. O capim dourado, fibra exclusiva da região, é a matéria-prima do artesanato mais famoso do Tocantins.\"," +
            "\"imagem\":\"/images/geral/amazonas3.1.jpg\"," +
            "\"lista\":[\"Área: 158.885 hectares de Cerrado preservado.\",\"Destaque: Fervedouros — nascentes que fervem de tão cristalinas.\",\"Artesanato: Capim dourado, fibra exclusiva do Jalapão.\"]}," +
            "\"experiencias\":{\"label\":\"Experiências\",\"titulo\":\"Aventura no Coração do Cerrado\"," +
            "\"texto\":\"Experiências únicas para amantes da natureza e do ecoturismo.\"," +
            "\"imagem\":\"/images/geral/oam.jpg\"," +
            "\"subsecoes\":[{\"titulo\":\"Fervedouros\",\"texto\":\"Os fervedouros são nascentes de água subterrânea que emergem com tanta pressão que parecem \\\"ferver\\\". A água cristalina e a pressão natural criam uma sensação única de flutuação, tornando o banho uma experiência inesquecível.\"}," +
            "{\"titulo\":\"Dunas de Areia\",\"texto\":\"As dunas douradas do Jalapão chegam a 40 metros de altura e são formadas pela areia do Rio Novo. Subir as dunas ao entardecer e contemplar o pôr do sol sobre o Cerrado é um dos momentos mais mágicos do parque.\"}," +
            "{\"titulo\":\"Cachoeiras e Trilhas\",\"texto\":\"O parque abriga diversas cachoeiras, como a Cachoeira da Velha e a Cachoeira do Formiga. As trilhas ecológicas percorrem o Cerrado com guias especializados, revelando a flora e fauna únicas do bioma.\"}]}," +
            "\"visita\":{\"label\":\"Visite\",\"titulo\":\"Como Visitar o Jalapão\"," +
            "\"texto\":\"O Jalapão exige veículo 4x4 e guia credenciado. O acesso é feito a partir de Palmas ou de Barreiras (BA).\"," +
            "\"imagem\":\"/images/geral/amazonas1.avif\"," +
            "\"subsecoes\":[{\"titulo\":\"Como Chegar\",\"texto\":\"De Palmas, siga pela TO-010 até Mateiros (320 km, sendo 200 km de estrada de terra). Veículo 4x4 é obrigatório. Agências de turismo em Palmas oferecem pacotes completos com transporte e guia.\"}," +
            "{\"titulo\":\"Melhor Época\",\"texto\":\"De junho a setembro, na estação seca, as estradas estão em melhores condições e os fervedouros mais acessíveis. De outubro a maio, as chuvas podem tornar as estradas intransitáveis.\"}]," +
            "\"recomendacoes\":[{\"titulo\":\"Agências de Turismo\",\"itens\":[{\"nome\":\"Jalapão Ecoturismo\",\"nota\":4.9,\"contato\":\"(63) 9999-1234\",\"site\":\"https://www.instagram.com/\"},{\"nome\":\"Tocantins Aventura\",\"nota\":4.8,\"contato\":\"(63) 9888-5678\",\"site\":\"https://www.instagram.com/\"}]}," +
            "{\"titulo\":\"Onde Ficar\",\"itens\":[{\"nome\":\"Pousada do Jalapão\",\"nota\":4.8,\"contato\":\"(63) 9777-1234\",\"site\":\"https://www.instagram.com/\"},{\"nome\":\"Hotel Mateiros\",\"nota\":4.6,\"contato\":\"(63) 9666-5678\",\"site\":\"https://www.instagram.com/\"}]}]}," +
            "\"fotos\":{\"label\":\"Fotos\"},\"avaliacoes\":{\"label\":\"Avaliações\"}}}");

        System.out.println("DataInitializer: informacoesAdicionais verificadas/inseridas.");
    }

    private void seedInfoLocal(String rota, String json) {
        localRepository.findByRotaFrontend(rota).ifPresent(local -> {
            local.setInformacoesAdicionais(json);
            localRepository.save(local);
        });
    }
}
