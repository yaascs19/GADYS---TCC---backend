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
            "{\"carouselImages\":[\"/images/geral/tea-am1.jpg\",\"/images/geral/tea-am3.jpg\"]," +
            "\"galleryImages\":[{\"src\":\"/images/geral/tea-am4.jpg\"},{\"src\":\"/images/geral/tea-am5.jpg\"},{\"src\":\"/images/geral/tea-am8.jpg\"},{\"src\":\"/images/geral/tea-am9.jpg\"},{\"src\":\"/images/geral/tea-am10.jpg\"},{\"src\":\"/images/geral/tea-am11.jpg\"}]," +
            "\"secoes\":{\"sobre\":{\"label\":\"Sobre\",\"titulo\":\"O Palácio da Floresta\"," +
            "\"texto\":\"Inaugurado em 1896 durante o auge do ciclo da borracha, o Teatro Amazonas é o símbolo máximo da opulência e da cultura de Manaus. Construído com materiais importados da Europa, sua cúpula colorida com as cores da bandeira do Brasil é reconhecida mundialmente.\"," +
            "\"imagem\":\"/images/monumentos/teatro-amazonas1.jpeg\"}," +
            "\"visita\":{\"label\":\"Visite\",\"titulo\":\"Informações Práticas\",\"texto\":\"\"," +
            "\"subsecoes\":[{\"titulo\":\"Horário de Funcionamento\",\"texto\":\"Terça a domingo, das 9h às 17h. Visitas guiadas disponíveis.\"}," +
            "{\"titulo\":\"Preço / Entrada\",\"texto\":\"R$ 20,00 (inteira) | R$ 10,00 (meia). Gratuito às terças-feiras.\"}]}," +
            "\"fotos\":{\"label\":\"Fotos\"},\"avaliacoes\":{\"label\":\"Avaliações\"}}}");

        seedInfoLocal("/arquipelago-anavilhanas",
            "{\"carouselImages\":[\"/images/geral/am-an1.jpg\",\"/images/geral/am-an2.jpg\"]," +
            "\"galleryImages\":[{\"src\":\"/images/geral/am-an3.webp\"},{\"src\":\"/images/geral/am-an4.jpg\"},{\"src\":\"/images/geral/am-an5.jpg\"},{\"src\":\"/images/geral/am-an6.jpg\"}]," +
            "\"secoes\":{\"sobre\":{\"label\":\"Sobre\",\"titulo\":\"O Labirinto de Ilhas do Rio Negro\"," +
            "\"texto\":\"O Arquipélago de Anavilhanas é um dos maiores arquipélagos fluviais do mundo, com mais de 400 ilhas, ilhotas e paranás no Rio Negro. Durante a cheia, as copas das árvores emergem das águas escuras criando um cenário surreal.\"," +
            "\"imagem\":\"/images/natureza/anavilhas.jpeg\"}," +
            "\"visita\":{\"label\":\"Visite\",\"titulo\":\"Informações Práticas\",\"texto\":\"\"," +
            "\"subsecoes\":[{\"titulo\":\"Como Chegar\",\"texto\":\"A 180 km de Manaus, pela AM-352. Passeios de barco saem de Novo Airão.\"}]}," +
            "\"fotos\":{\"label\":\"Fotos\"},\"avaliacoes\":{\"label\":\"Avaliações\"}}}");

        seedInfoLocal("/bumbodromo",
            "{\"carouselImages\":[\"/images/geral/am-bun1.avif\",\"/images/geral/am-bun2.jpeg\"]," +
            "\"galleryImages\":[{\"src\":\"/images/geral/am-bun3.jpeg\"},{\"src\":\"/images/geral/am-bun4.jpeg\"},{\"src\":\"/images/geral/am-bun5.jpg\"},{\"src\":\"/images/geral/am-bun6.jpeg\"}]," +
            "\"secoes\":{\"sobre\":{\"label\":\"Sobre\",\"titulo\":\"O Templo do Boi-Bumbá\"," +
            "\"texto\":\"O Bumbódromo é a arena a céu aberto de Parintins, palco do maior festival folclórico do Brasil. Com capacidade para 35 mil pessoas, recebe anualmente o Festival de Parintins, onde os bois Garantido e Caprichoso disputam em uma batalha de cores, música e dança.\"," +
            "\"imagem\":\"/images/geral/bum-Am.jpeg\"}," +
            "\"visita\":{\"label\":\"Visite\",\"titulo\":\"Informações Práticas\",\"texto\":\"\"," +
            "\"subsecoes\":[{\"titulo\":\"Festival de Parintins\",\"texto\":\"Realizado no último fim de semana de junho. Reserve passagens e hospedagem com meses de antecedência.\"}]}," +
            "\"fotos\":{\"label\":\"Fotos\"},\"avaliacoes\":{\"label\":\"Avaliações\"}}}");

        seedInfoLocal("/cachoeira-santuario",
            "{\"carouselImages\":[\"/images/geral/am-cs1.jpg\",\"/images/geral/am-cs2.jpg\"]," +
            "\"galleryImages\":[{\"src\":\"/images/geral/am-cs3.jpg\"},{\"src\":\"/images/geral/am-cs4.jpg\"},{\"src\":\"/images/geral/am-cs5.jpg\"},{\"src\":\"/images/geral/am-cs6.jpg\"}]," +
            "\"secoes\":{\"sobre\":{\"label\":\"Sobre\",\"titulo\":\"A Joia Escondida da Amazônia\"," +
            "\"texto\":\"A Cachoeira do Santuário é uma das mais belas quedas d'água da Amazônia, localizada em Presidente Figueiredo, a 107 km de Manaus. Com piscinas naturais de água cristalina e trilhas na floresta, é um destino imperdível para os amantes da natureza.\"," +
            "\"imagem\":\"/images/geral/am-cs7.jpg\"}," +
            "\"visita\":{\"label\":\"Visite\",\"titulo\":\"Informações Práticas\",\"texto\":\"\"," +
            "\"subsecoes\":[{\"titulo\":\"Como Chegar\",\"texto\":\"A 107 km de Manaus pela AM-010. Acesso por trilha de 1,5 km.\"}]}," +
            "\"fotos\":{\"label\":\"Fotos\"},\"avaliacoes\":{\"label\":\"Avaliações\"}}}");

        // ── CEARÁ ──
        seedInfoLocal("/ceara/jericoacoara",
            "{\"carouselImages\":[\"/images/geral/Ceara1.webp\",\"/images/geral/Ceara2.webp\"]," +
            "\"galleryImages\":[{\"src\":\"/images/geral/Ceara3.jpg\"},{\"src\":\"/images/geral/CearaInicio.jpg\"}]," +
            "\"secoes\":{\"sobre\":{\"label\":\"Sobre\",\"titulo\":\"O Paraíso Escondido do Brasil\"," +
            "\"texto\":\"Jericoacoara, carinhosamente chamada de Jeri, é uma vila paradisíaca encravada entre dunas, lagoas e o mar. Considerada uma das praias mais bonitas do mundo pela revista Condé Nast Traveler.\"," +
            "\"imagem\":\"/images/geral/Ceara1.webp\"," +
            "\"lista\":[\"Localização: Jijoca de Jericoacoara, a 300 km de Fortaleza.\",\"Destaque: Pôr do sol na Pedra Furada.\",\"Esportes: Kitesurf e windsurf entre os melhores do planeta.\"]}," +
            "\"experiencias\":{\"label\":\"Experiências\",\"titulo\":\"Viva Jeri ao Máximo\"," +
            "\"texto\":\"Jericoacoara oferece experiências únicas para todos os perfis de viajante.\"," +
            "\"imagem\":\"/images/geral/Ceara2.webp\"," +
            "\"subsecoes\":[{\"titulo\":\"Lagoa do Paraíso e Lagoa Azul\",\"texto\":\"As lagoas de água doce cristalina são o cartão-postal de Jeri.\"}," +
            "{\"titulo\":\"Kitesurf e Windsurf\",\"texto\":\"Os ventos constantes tornaram Jeri um dos melhores destinos do mundo para kitesurf.\"}," +
            "{\"titulo\":\"Dunas e Pôr do Sol\",\"texto\":\"Subir a Duna do Pôr do Sol para assistir ao espetáculo diário é um ritual sagrado em Jeri.\"}]}," +
            "\"visita\":{\"label\":\"Visite\",\"titulo\":\"Como Chegar e Se Hospedar\"," +
            "\"texto\":\"Chegar a Jericoacoara faz parte da aventura. O acesso é feito por veículos 4x4 ou buggys.\"," +
            "\"imagem\":\"/images/geral/Ceara3.jpg\"," +
            "\"subsecoes\":[{\"titulo\":\"Como Chegar\",\"texto\":\"De Fortaleza, pegue um ônibus ou van até Jijoca (aprox. 4h). De lá, 4x4s fazem o trajeto pelas dunas.\"}," +
            "{\"titulo\":\"Melhor Época\",\"texto\":\"De julho a dezembro, quando os ventos são mais fortes.\"}]," +
            "\"recomendacoes\":[{\"titulo\":\"Onde Comer\",\"itens\":[{\"nome\":\"Restaurante Estoril\",\"nota\":4.8,\"contato\":\"(88) 3669-2066\",\"site\":\"https://www.instagram.com/estoril.jeri/\"}]}," +
            "{\"titulo\":\"Onde Ficar\",\"itens\":[{\"nome\":\"Vila Kalango\",\"nota\":4.9,\"contato\":\"(88) 3669-2289\",\"site\":\"https://www.vilakalango.com.br/\"}]}]}," +
            "\"fotos\":{\"label\":\"Fotos\"},\"avaliacoes\":{\"label\":\"Avaliações\"}}}");

        seedInfoLocal("/ceara/canoa-quebrada",
            "{\"carouselImages\":[\"/images/geral/Ceara1.webp\",\"/images/geral/Ceara2.webp\"]," +
            "\"galleryImages\":[{\"src\":\"/images/geral/Ceara3.jpg\"},{\"src\":\"/images/geral/CearaInicio.jpg\"}]," +
            "\"secoes\":{\"sobre\":{\"label\":\"Sobre\",\"titulo\":\"As Falésias Vermelhas do Ceará\"," +
            "\"texto\":\"Canoa Quebrada é uma das praias mais famosas do Nordeste. Suas falésias de arenito vermelho contrastam com a areia branca e o mar azul-turquesa.\"," +
            "\"imagem\":\"/images/geral/Ceara1.webp\"," +
            "\"lista\":[\"Localização: Aracati, a 164 km de Fortaleza.\",\"Símbolo: A lua e a estrela esculpidas nas falésias.\",\"Destaque: A Broadway, rua principal com bares e restaurantes.\"]}," +
            "\"experiencias\":{\"label\":\"Experiências\",\"titulo\":\"O Que Fazer em Canoa Quebrada\"," +
            "\"texto\":\"De passeios de buggy pelas falésias a noites animadas na Broadway.\"," +
            "\"imagem\":\"/images/geral/Ceara2.webp\"," +
            "\"subsecoes\":[{\"titulo\":\"Passeio de Buggy\",\"texto\":\"Os buggys percorrem as falésias e praias vizinhas como Majorlândia e Quixaba.\"}," +
            "{\"titulo\":\"A Broadway\",\"texto\":\"A rua principal ganha vida ao anoitecer com música ao vivo e forró.\"}]}," +
            "\"visita\":{\"label\":\"Visite\",\"titulo\":\"Planeje Sua Visita\",\"texto\":\"\"," +
            "\"imagem\":\"/images/geral/Ceara3.jpg\"," +
            "\"subsecoes\":[{\"titulo\":\"Como Chegar\",\"texto\":\"De Fortaleza, há ônibus regulares até Aracati (2h30). De lá, táxis até a praia (20 min).\"}]," +
            "\"recomendacoes\":[{\"titulo\":\"Onde Ficar\",\"itens\":[{\"nome\":\"Pousada Lua Estrela\",\"nota\":4.8,\"contato\":\"(88) 3421-7055\",\"site\":\"https://www.instagram.com/pousadaluaestrela/\"}]}]}," +
            "\"fotos\":{\"label\":\"Fotos\"},\"avaliacoes\":{\"label\":\"Avaliações\"}}}");

        seedInfoLocal("/ceara/dragao-do-mar",
            "{\"carouselImages\":[\"/images/geral/Ceara1.webp\",\"/images/geral/Ceara2.webp\"]," +
            "\"galleryImages\":[{\"src\":\"/images/geral/Ceara3.jpg\"},{\"src\":\"/images/geral/CearaInicio.jpg\"}]," +
            "\"secoes\":{\"sobre\":{\"label\":\"Sobre\",\"titulo\":\"Arte e Cultura no Coração de Fortaleza\"," +
            "\"texto\":\"O Centro Cultural Dragão do Mar é o maior complexo cultural do Ceará. Inaugurado em 1999, abriga museus, teatro, planetário, cinema e espaços de convivência.\"," +
            "\"imagem\":\"/images/geral/Ceara1.webp\"," +
            "\"lista\":[\"Localização: Praia de Iracema, Fortaleza - CE.\",\"Inauguração: 1999.\",\"Destaques: Museu de Arte Contemporânea, Planetário e Memorial da Cultura Cearense.\"]}," +
            "\"experiencias\":{\"label\":\"Experiências\",\"titulo\":\"O Que Explorar no Dragão do Mar\"," +
            "\"texto\":\"O complexo oferece programação cultural intensa com exposições, espetáculos e eventos.\"," +
            "\"imagem\":\"/images/geral/Ceara2.webp\"," +
            "\"subsecoes\":[{\"titulo\":\"Museu de Arte Contemporânea\",\"texto\":\"O MAC Ceará ocupa um dos espaços mais emblemáticos do centro.\"}," +
            "{\"titulo\":\"Planetário Rubens de Azevedo\",\"texto\":\"Um dos mais modernos do Brasil, com sessões de astronomia.\"}]}," +
            "\"visita\":{\"label\":\"Visite\",\"titulo\":\"Como Visitar\",\"texto\":\"\"," +
            "\"imagem\":\"/images/geral/Ceara3.jpg\"," +
            "\"subsecoes\":[{\"titulo\":\"Horários e Ingressos\",\"texto\":\"Terça a domingo, das 10h às 21h30. Entrada gratuita para espaços externos.\"}," +
            "{\"titulo\":\"Como Chegar\",\"texto\":\"Rua Dragão do Mar, 81, Praia de Iracema. Acessível por ônibus ou metrô.\"}]," +
            "\"recomendacoes\":[{\"titulo\":\"Onde Comer\",\"itens\":[{\"nome\":\"Restaurante Colher de Pau\",\"nota\":4.8,\"contato\":\"(85) 3219-3773\",\"site\":\"https://www.colherdepau.com.br/\"}]}]}," +
            "\"fotos\":{\"label\":\"Fotos\"},\"avaliacoes\":{\"label\":\"Avaliações\"}}}");

        seedInfoLocal("/ceara/beach-park",
            "{\"carouselImages\":[\"/images/geral/Ceara1.webp\",\"/images/geral/Ceara2.webp\"]," +
            "\"galleryImages\":[{\"src\":\"/images/geral/Ceara3.jpg\"},{\"src\":\"/images/geral/CearaInicio.jpg\"}]," +
            "\"secoes\":{\"sobre\":{\"label\":\"Sobre\",\"titulo\":\"Diversão Sem Limites no Ceará\"," +
            "\"texto\":\"O Beach Park é o maior parque aquático da América Latina. Localizado em Aquiraz, a 27 km de Fortaleza, reúne toboáguas radicais, piscinas de ondas e praia privativa.\"," +
            "\"imagem\":\"/images/geral/Ceara1.webp\"," +
            "\"lista\":[\"Localização: Aquiraz, a 27 km de Fortaleza.\",\"Área: Mais de 70.000 m² de atrações aquáticas.\",\"Destaque: Insano, o toboágua mais alto do mundo por anos.\"]}," +
            "\"experiencias\":{\"label\":\"Experiências\",\"titulo\":\"Adrenalina e Diversão para Todos\"," +
            "\"texto\":\"Com mais de 20 atrações aquáticas para todas as idades.\"," +
            "\"imagem\":\"/images/geral/Ceara2.webp\"," +
            "\"subsecoes\":[{\"titulo\":\"Insano e Atrações Radicais\",\"texto\":\"O Insano, com 41 metros de altura, foi por anos o toboágua mais alto do mundo.\"}," +
            "{\"titulo\":\"Área Infantil\",\"texto\":\"O Acqua Kids é um paraíso para as crianças, com toboáguas menores e piscinas rasas.\"}]}," +
            "\"visita\":{\"label\":\"Visite\",\"titulo\":\"Planeje Seu Dia no Beach Park\",\"texto\":\"\"," +
            "\"imagem\":\"/images/geral/Ceara3.jpg\"," +
            "\"subsecoes\":[{\"titulo\":\"Ingressos e Horários\",\"texto\":\"Quarta a domingo e feriados, das 11h às 17h. Ingressos a partir de R$ 180.\"}," +
            "{\"titulo\":\"Como Chegar\",\"texto\":\"De Fortaleza, há ônibus direto saindo do Terminal Papicu. Pela CE-040.\"}]," +
            "\"recomendacoes\":[{\"titulo\":\"Onde Ficar\",\"itens\":[{\"nome\":\"Beach Park Suites Resort\",\"nota\":4.8,\"contato\":\"(85) 4012-3000\",\"site\":\"https://www.beachpark.com.br/\"}]}]}," +
            "\"fotos\":{\"label\":\"Fotos\"},\"avaliacoes\":{\"label\":\"Avaliações\"}}}");

        seedInfoLocal("/ceara/praia-do-futuro",
            "{\"carouselImages\":[\"/images/geral/Ceara1.webp\",\"/images/geral/Ceara2.webp\"]," +
            "\"galleryImages\":[{\"src\":\"/images/geral/Ceara3.jpg\"},{\"src\":\"/images/geral/CearaInicio.jpg\"}]," +
            "\"secoes\":{\"sobre\":{\"label\":\"Sobre\",\"titulo\":\"O Coração das Praias de Fortaleza\"," +
            "\"texto\":\"A Praia do Futuro é a mais popular de Fortaleza. Com 8 km de extensão, águas mornas e barracas estruturadas, é o destino favorito dos fortalezenses.\"," +
            "\"imagem\":\"/images/geral/Ceara1.webp\"," +
            "\"lista\":[\"Localização: Zona Leste de Fortaleza, a 8 km do centro.\",\"Extensão: 8 km de praia.\",\"Destaque: Barracas de frutos do mar com música ao vivo.\"]}," +
            "\"experiencias\":{\"label\":\"Experiências\",\"titulo\":\"Um Dia Perfeito na Praia do Futuro\"," +
            "\"texto\":\"As barracas são verdadeiros complexos de lazer com piscinas, shows e gastronomia.\"," +
            "\"imagem\":\"/images/geral/Ceara2.webp\"," +
            "\"subsecoes\":[{\"titulo\":\"As Barracas Famosas\",\"texto\":\"Chico do Caranguejo, Crocobeach e Barraca do Meio são algumas das mais tradicionais.\"}]}," +
            "\"visita\":{\"label\":\"Visite\",\"titulo\":\"Como Aproveitar\",\"texto\":\"\"," +
            "\"imagem\":\"/images/geral/Ceara3.jpg\"," +
            "\"subsecoes\":[{\"titulo\":\"Como Chegar\",\"texto\":\"De ônibus, pegue as linhas que passam pela Av. Zezé Diogo.\"}]," +
            "\"recomendacoes\":[{\"titulo\":\"Barracas Imperdíveis\",\"itens\":[{\"nome\":\"Chico do Caranguejo\",\"nota\":4.8,\"contato\":\"(85) 3262-0022\",\"site\":\"https://www.chicodocara nguejo.com.br/\"},{\"nome\":\"Crocobeach\",\"nota\":4.7,\"contato\":\"(85) 3234-4444\",\"site\":\"https://www.crocobeach.com.br/\"}]}]}," +
            "\"fotos\":{\"label\":\"Fotos\"},\"avaliacoes\":{\"label\":\"Avaliações\"}}}");

        seedInfoLocal("/ceara/serra-de-baturite",
            "{\"carouselImages\":[\"/images/geral/Ceara1.webp\",\"/images/geral/Ceara2.webp\"]," +
            "\"galleryImages\":[{\"src\":\"/images/geral/Ceara3.jpg\"},{\"src\":\"/images/geral/CearaInicio.jpg\"}]," +
            "\"secoes\":{\"sobre\":{\"label\":\"Sobre\",\"titulo\":\"O Oásis Verde do Ceará\"," +
            "\"texto\":\"A Serra de Baturité é um verdadeiro oásis no semiárido cearense. Com altitude de até 1.114 metros, a região desfruta de clima ameno e úmido.\"," +
            "\"imagem\":\"/images/geral/Ceara1.webp\"," +
            "\"lista\":[\"Localização: A 100 km de Fortaleza.\",\"Altitude: Até 1.114 metros no Pico Alto.\",\"Clima: Ameno, entre 18°C e 25°C.\"]}," +
            "\"experiencias\":{\"label\":\"Experiências\",\"titulo\":\"Natureza e Cultura na Serra\"," +
            "\"texto\":\"A Serra de Baturité oferece ecoturismo, gastronomia e cultura caipira cearense.\"," +
            "\"imagem\":\"/images/geral/Ceara2.webp\"," +
            "\"subsecoes\":[{\"titulo\":\"Cachoeiras e Trilhas\",\"texto\":\"A região abriga diversas cachoeiras como a Cachoeira do Pinga e a Cachoeira do Urubu.\"}," +
            "{\"titulo\":\"Rota do Café\",\"texto\":\"Fazendas históricas abrem suas portas para visitas guiadas e degustação do café local.\"}]}," +
            "\"visita\":{\"label\":\"Visite\",\"titulo\":\"Como Visitar\",\"texto\":\"\"," +
            "\"imagem\":\"/images/geral/Ceara3.jpg\"," +
            "\"subsecoes\":[{\"titulo\":\"Como Chegar\",\"texto\":\"De Fortaleza, siga pela BR-222 até Pacatuba, depois pela CE-065 até Baturité (aprox. 1h30).\"}]," +
            "\"recomendacoes\":[{\"titulo\":\"Onde Ficar\",\"itens\":[{\"nome\":\"Pousada Recanto da Serra\",\"nota\":4.7,\"contato\":\"(85) 3325-1111\",\"site\":\"https://www.instagram.com/\"}]}]}," +
            "\"fotos\":{\"label\":\"Fotos\"},\"avaliacoes\":{\"label\":\"Avaliações\"}}}");

        seedInfoLocal("/ceara/chapada-do-araripe",
            "{\"carouselImages\":[\"/images/geral/Ceara1.webp\",\"/images/geral/Ceara2.webp\"]," +
            "\"galleryImages\":[{\"src\":\"/images/geral/Ceara3.jpg\"},{\"src\":\"/images/geral/CearaInicio.jpg\"}]," +
            "\"secoes\":{\"sobre\":{\"label\":\"Sobre\",\"titulo\":\"O Tesouro Paleontológico do Brasil\"," +
            "\"texto\":\"A Chapada do Araripe é um planalto sedimentar com altitude média de 900 metros. Abriga um dos mais importantes sítios paleontológicos do mundo e fontes de água cristalina.\"," +
            "\"imagem\":\"/images/geral/Ceara1.webp\"," +
            "\"lista\":[\"Localização: Crato e Juazeiro do Norte, sul do Ceará.\",\"Altitude: Média de 900 metros.\",\"Destaque: Geopark Araripe, primeiro geopark das Américas reconhecido pela UNESCO.\"]}," +
            "\"experiencias\":{\"label\":\"Experiências\",\"titulo\":\"Natureza e Ciência na Chapada\"," +
            "\"texto\":\"A Chapada combina beleza natural, história geológica e espiritualidade.\"," +
            "\"imagem\":\"/images/geral/Ceara2.webp\"," +
            "\"subsecoes\":[{\"titulo\":\"Fontes e Balneários\",\"texto\":\"As fontes de água doce formam balneários naturais de beleza rara.\"}," +
            "{\"titulo\":\"Geopark Araripe\",\"texto\":\"Reúne sítios geológicos e paleontológicos de importância mundial.\"}]}," +
            "\"visita\":{\"label\":\"Visite\",\"titulo\":\"Como Visitar\",\"texto\":\"\"," +
            "\"imagem\":\"/images/geral/Ceara3.jpg\"," +
            "\"subsecoes\":[{\"titulo\":\"Como Chegar\",\"texto\":\"De Fortaleza, há voos e ônibus para Juazeiro do Norte (aprox. 6h de ônibus).\"}]," +
            "\"recomendacoes\":[{\"titulo\":\"Onde Ficar\",\"itens\":[{\"nome\":\"Hotel Panorama Juazeiro\",\"nota\":4.6,\"contato\":\"(88) 3512-1000\",\"site\":\"https://www.instagram.com/\"}]}]}," +
            "\"fotos\":{\"label\":\"Fotos\"},\"avaliacoes\":{\"label\":\"Avaliações\"}}}");

        seedInfoLocal("/ceara/centro-historico-fortaleza",
            "{\"carouselImages\":[\"/images/geral/Ceara1.webp\",\"/images/geral/Ceara2.webp\"]," +
            "\"galleryImages\":[{\"src\":\"/images/geral/Ceara3.jpg\"},{\"src\":\"/images/geral/CearaInicio.jpg\"}]," +
            "\"secoes\":{\"sobre\":{\"label\":\"Sobre\",\"titulo\":\"A Alma Histórica de Fortaleza\"," +
            "\"texto\":\"O Centro Histórico de Fortaleza concentra os principais monumentos e museus da capital cearense. Do Theatro José de Alencar ao Mercado Central, cada esquina conta uma história.\"," +
            "\"imagem\":\"/images/geral/Ceara1.webp\"," +
            "\"lista\":[\"Localização: Centro de Fortaleza.\",\"Destaque: Theatro José de Alencar, obra do Art Nouveau brasileiro.\",\"Compras: Mercado Central com artesanato e produtos típicos.\"]}," +
            "\"experiencias\":{\"label\":\"Experiências\",\"titulo\":\"O Que Explorar no Centro\"," +
            "\"texto\":\"O Centro Histórico é um roteiro cultural completo com monumentos, museus e gastronomia.\"," +
            "\"imagem\":\"/images/geral/Ceara2.webp\"," +
            "\"subsecoes\":[{\"titulo\":\"Theatro José de Alencar\",\"texto\":\"Inaugurado em 1910, obra-prima do Art Nouveau com estrutura metálica importada da Escócia.\"}," +
            "{\"titulo\":\"Mercado Central\",\"texto\":\"Com mais de 600 boxes, é o maior mercado de artesanato do Ceará.\"}]}," +
            "\"visita\":{\"label\":\"Visite\",\"titulo\":\"Como Explorar\",\"texto\":\"\"," +
            "\"imagem\":\"/images/geral/Ceara3.jpg\"," +
            "\"subsecoes\":[{\"titulo\":\"Como Chegar\",\"texto\":\"Acessível por todas as linhas de ônibus de Fortaleza. Metrô: estações José de Alencar e Colégio Militar.\"}]," +
            "\"recomendacoes\":[{\"titulo\":\"Onde Comer\",\"itens\":[{\"nome\":\"Restaurante Colher de Pau\",\"nota\":4.8,\"contato\":\"(85) 3219-3773\",\"site\":\"https://www.colherdepau.com.br/\"}]}]}," +
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
            "\"recomendacoes\":[{\"titulo\":\"Onde Comer\",\"itens\":[{\"nome\":\"Restaurante Chafariz\",\"nota\":4.8,\"contato\":\"(31) 3551-2828\",\"site\":\"https://www.instagram.com/\"}]}," +
            "{\"titulo\":\"Onde Ficar\",\"itens\":[{\"nome\":\"Pousada do Mondego\",\"nota\":4.8,\"contato\":\"(31) 3551-2040\",\"site\":\"https://www.pousadadomondego.com.br/\"}]}]}," +
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
            "\"recomendacoes\":[{\"titulo\":\"Onde Comer\",\"itens\":[{\"nome\":\"Restaurante Tamboril\",\"nota\":4.8,\"contato\":\"(31) 3571-9700\",\"site\":\"https://www.inhotim.org.br/\"}]}," +
            "{\"titulo\":\"Onde Ficar\",\"itens\":[{\"nome\":\"Pousada Inhotim\",\"nota\":4.9,\"contato\":\"(31) 3571-9700\",\"site\":\"https://www.inhotim.org.br/\"}]}]}," +
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
            "\"subsecoes\":[{\"titulo\":\"Trilha da Pedra Azul\",\"texto\":\"A trilha principal percorre 2,5 km até o mirante, com desnível de 400 metros e piscinas naturais ao longo do caminho.\"}," +
            "{\"titulo\":\"Observação de Aves\",\"texto\":\"O parque tem mais de 200 espécies registradas, incluindo o beija-flor-de-fronte-violeta e o tucano-de-bico-verde.\"}]}," +
            "\"visita\":{\"label\":\"Visite\",\"titulo\":\"Como Visitar a Pedra Azul\",\"texto\":\"\"," +
            "\"imagem\":\"/images/natureza/chapada.jpeg\"," +
            "\"subsecoes\":[{\"titulo\":\"Horários e Ingressos\",\"texto\":\"Terça a domingo, das 8h às 17h. Ingresso: R$ 20 adulto. Trilha requer agendamento e guia credenciado.\"}," +
            "{\"titulo\":\"Como Chegar\",\"texto\":\"De Vitória, siga pela BR-262 até Domingos Martins (90 km). Há ônibus regulares de Vitória.\"}]," +
            "\"recomendacoes\":[{\"titulo\":\"Onde Ficar\",\"itens\":[{\"nome\":\"Pousada Pedra Azul\",\"nota\":4.8,\"contato\":\"(27) 3248-1234\",\"site\":\"https://www.instagram.com/\"}]}]}," +
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
            "\"subsecoes\":[{\"titulo\":\"Praias Principais\",\"texto\":\"A Praia do Morro (4 km) é a mais movimentada. Meaípe é famosa pelos frutos do mar. Castanheiras é ideal para famílias.\"}," +
            "{\"titulo\":\"Mergulho e Esportes\",\"texto\":\"Águas claras com visibilidade de até 15 metros. Passeios de barco, stand-up paddle e kitesurf são populares.\"}]}," +
            "\"visita\":{\"label\":\"Visite\",\"titulo\":\"Como Chegar a Guarapari\",\"texto\":\"\"," +
            "\"imagem\":\"/images/natureza/veadeiros.jpeg\"," +
            "\"subsecoes\":[{\"titulo\":\"Como Chegar\",\"texto\":\"De Vitória, siga pela BR-101 Sul (53 km, 45 min). Há ônibus regulares do Terminal de Vitória.\"}]," +
            "\"recomendacoes\":[{\"titulo\":\"Onde Comer\",\"itens\":[{\"nome\":\"Restaurante Meaípe\",\"nota\":4.8,\"contato\":\"(27) 3272-1234\",\"site\":\"https://www.instagram.com/\"}]}," +
            "{\"titulo\":\"Onde Ficar\",\"itens\":[{\"nome\":\"Hotel Praia do Morro\",\"nota\":4.6,\"contato\":\"(27) 3261-1000\",\"site\":\"https://www.instagram.com/\"}]}]}," +
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
            "\"subsecoes\":[{\"titulo\":\"Observação de Fauna\",\"texto\":\"Onças-pintadas, antas, ariranhas e centenas de espécies de aves habitam o parque.\"}," +
            "{\"titulo\":\"Passeios de Barco\",\"texto\":\"Os rios Chandless e Purus são as principais vias de acesso, permitindo explorar a floresta de igapó.\"}]}," +
            "\"visita\":{\"label\":\"Visite\",\"titulo\":\"Como Visitar o Parque Chandless\",\"texto\":\"\"," +
            "\"imagem\":\"/images/geral/amazonas3.1.jpg\"," +
            "\"subsecoes\":[{\"titulo\":\"Como Chegar\",\"texto\":\"De Rio Branco, voos fretados ou barcos pelo Rio Purus até Santa Rosa do Purus. O trajeto pode levar de 2 a 5 dias por via fluvial.\"}," +
            "{\"titulo\":\"Melhor Época\",\"texto\":\"De junho a outubro, na estação seca, os rios ficam mais baixos e as praias fluviais aparecem.\"}]," +
            "\"recomendacoes\":[{\"titulo\":\"Operadoras de Ecoturismo\",\"itens\":[{\"nome\":\"Acre Ecoturismo\",\"nota\":4.8,\"contato\":\"(68) 9999-1234\",\"site\":\"https://www.instagram.com/\"}]}]}," +
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
            "\"subsecoes\":[{\"titulo\":\"Museu da Borracha\",\"texto\":\"Preserva a memória do ciclo da borracha com ferramentas, fotografias e documentos históricos.\"}," +
            "{\"titulo\":\"Calçadão da Gameleira\",\"texto\":\"O calçadão às margens do Rio Acre é o coração da vida social de Rio Branco, com feiras e pôr do sol deslumbrante.\"}]}," +
            "\"visita\":{\"label\":\"Visite\",\"titulo\":\"Como Explorar Rio Branco\",\"texto\":\"\"," +
            "\"imagem\":\"/images/geral/oam.jpg\"," +
            "\"subsecoes\":[{\"titulo\":\"Como Chegar\",\"texto\":\"Rio Branco tem voos diretos de São Paulo, Brasília e Manaus. O aeroporto fica a 22 km do centro.\"}]," +
            "\"recomendacoes\":[{\"titulo\":\"Onde Comer\",\"itens\":[{\"nome\":\"Restaurante Casarão\",\"nota\":4.7,\"contato\":\"(68) 3224-1234\",\"site\":\"https://www.instagram.com/\"}]}," +
            "{\"titulo\":\"Onde Ficar\",\"itens\":[{\"nome\":\"Hotel Inácio Palace\",\"nota\":4.6,\"contato\":\"(68) 3224-6300\",\"site\":\"https://www.instagram.com/\"}]}]}," +
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
            "\"subsecoes\":[{\"titulo\":\"Visita Guiada\",\"texto\":\"Guias conduzem pelos baluartes e casamatas, contando a história das batalhas para defender a fronteira norte.\"}," +
            "{\"titulo\":\"Vista do Rio Amazonas\",\"texto\":\"Da muralha, vista deslumbrante do Rio Amazonas. Ao entardecer, o pôr do sol sobre o rio é inesquecível.\"}]}," +
            "\"visita\":{\"label\":\"Visite\",\"titulo\":\"Como Visitar a Fortaleza\",\"texto\":\"\"," +
            "\"imagem\":\"/images/geral/oam.jpg\"," +
            "\"subsecoes\":[{\"titulo\":\"Horários e Ingressos\",\"texto\":\"Terça a domingo, das 9h às 18h. Entrada gratuita. Visitas guiadas às 10h e 15h.\"}," +
            "{\"titulo\":\"Como Chegar\",\"texto\":\"Rua Cândido Mendes, centro de Macapá. A 500m do Marco Zero do Equador.\"}]," +
            "\"recomendacoes\":[{\"titulo\":\"Onde Ficar\",\"itens\":[{\"nome\":\"Hotel Novotel Macapá\",\"nota\":4.6,\"contato\":\"(96) 3198-3000\",\"site\":\"https://www.instagram.com/\"}]}]}," +
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
            "\"subsecoes\":[{\"titulo\":\"Museu Ferroviário\",\"texto\":\"Preserva locomotivas originais, vagões e documentos históricos da ferrovia.\"}," +
            "{\"titulo\":\"Orla do Rio Madeira\",\"texto\":\"O museu fica às margens do Rio Madeira. Após a visita, aproveite o pôr do sol sobre o maior afluente do Amazonas.\"}]}," +
            "\"visita\":{\"label\":\"Visite\",\"titulo\":\"Como Visitar o Museu\",\"texto\":\"\"," +
            "\"imagem\":\"/images/geral/amazonas3.1.jpg\"," +
            "\"subsecoes\":[{\"titulo\":\"Horários e Ingressos\",\"texto\":\"Terça a domingo, das 8h às 17h. Entrada gratuita.\"}," +
            "{\"titulo\":\"Como Chegar\",\"texto\":\"Av. Farquar, às margens do Rio Madeira, centro de Porto Velho.\"}]," +
            "\"recomendacoes\":[{\"titulo\":\"Onde Ficar\",\"itens\":[{\"nome\":\"Hotel Vila Rica Porto Velho\",\"nota\":4.6,\"contato\":\"(69) 3216-3000\",\"site\":\"https://www.instagram.com/\"}]}]}," +
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
            "\"subsecoes\":[{\"titulo\":\"Trekking ao Topo\",\"texto\":\"O trekking completo dura de 8 a 12 dias partindo da aldeia Paraitepui. O topo é coberto por plantas carnivoras, cristais de quartzo e piscinas naturais.\"}," +
            "{\"titulo\":\"Tríplice Fronteira\",\"texto\":\"Do topo, é possível ver Brasil, Venezuela e Guiana simultaneamente.\"}]}," +
            "\"visita\":{\"label\":\"Visite\",\"titulo\":\"Como Fazer o Trekking\",\"texto\":\"\"," +
            "\"imagem\":\"/images/geral/amazonas2.jpg\"," +
            "\"subsecoes\":[{\"titulo\":\"Como Chegar\",\"texto\":\"De Boa Vista, ônibus até Pacaraima (215 km), depois até a aldeia Paraitepui (80 km de estrada de terra). Guia indígena obrigatório.\"}," +
            "{\"titulo\":\"Melhor Época\",\"texto\":\"De dezembro a abril as cachoeiras estão mais cheias. De maio a novembro as trilhas são mais fáceis.\"}]," +
            "\"recomendacoes\":[{\"titulo\":\"Agências de Trekking\",\"itens\":[{\"nome\":\"Roraima Adventures\",\"nota\":4.9,\"contato\":\"(95) 9999-1234\",\"site\":\"https://www.instagram.com/\"}]}]}," +
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
            "\"subsecoes\":[{\"titulo\":\"Fervedouros\",\"texto\":\"Nascentes com pressão tão forte que parecem ferver. A água cristalina cria uma sensação única de flutuação.\"}," +
            "{\"titulo\":\"Dunas de Areia\",\"texto\":\"Dunas douradas de até 40 metros. Subir ao entardecer para o pôr do sol sobre o Cerrado é um dos momentos mais mágicos.\"}," +
            "{\"titulo\":\"Cachoeiras e Trilhas\",\"texto\":\"Cachoeira da Velha e Cachoeira do Formiga são as mais famosas. Trilhas com guias especializados.\"}]}," +
            "\"visita\":{\"label\":\"Visite\",\"titulo\":\"Como Visitar o Jalapão\",\"texto\":\"\"," +
            "\"imagem\":\"/images/geral/amazonas1.avif\"," +
            "\"subsecoes\":[{\"titulo\":\"Como Chegar\",\"texto\":\"De Palmas, siga pela TO-010 até Mateiros (320 km, sendo 200 km de estrada de terra). Veículo 4x4 obrigatório.\"}," +
            "{\"titulo\":\"Melhor Época\",\"texto\":\"De junho a setembro, na estação seca, as estradas estão em melhores condições.\"}]," +
            "\"recomendacoes\":[{\"titulo\":\"Agências de Turismo\",\"itens\":[{\"nome\":\"Jalapão Ecoturismo\",\"nota\":4.9,\"contato\":\"(63) 9999-1234\",\"site\":\"https://www.instagram.com/\"}]}," +
            "{\"titulo\":\"Onde Ficar\",\"itens\":[{\"nome\":\"Pousada do Jalapão\",\"nota\":4.8,\"contato\":\"(63) 9777-1234\",\"site\":\"https://www.instagram.com/\"}]}]}," +
            "\"fotos\":{\"label\":\"Fotos\"},\"avaliacoes\":{\"label\":\"Avaliações\"}}}");

        System.out.println("DataInitializer: informacoesAdicionais verificadas/inseridas.");
    }

    private void seedInfoLocal(String rota, String json) {
        localRepository.findByRotaFrontend(rota).ifPresent(local -> {
            if (local.getInformacoesAdicionais() == null) {
                local.setInformacoesAdicionais(json);
                localRepository.save(local);
            }
        });
    }
}
