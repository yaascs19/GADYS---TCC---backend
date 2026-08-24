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
            new Object[]{"Forte de São José da Barra", "Fortaleza histórica construída em 1669, marco da colonização portuguesa em Manaus.", "lugares-visitar", "monumentos", "Manaus", "AM", null},
            new Object[]{"Palácio da Justiça", "Edifício histórico com arquitetura neoclássica de 1900. Hoje funciona como museu.", "lugares-visitar", "monumentos", "Manaus", "AM", null},
            new Object[]{"Mercado Municipal Adolpho Lisboa", "Mercado histórico de 1883 inspirado no Les Halles de Paris. Patrimônio de Manaus.", "lugares-visitar", "monumentos", "Manaus", "AM", null},
            new Object[]{"Igreja de São Sebastião", "Igreja colonial do século XVIII na Praça São Sebastião, em frente ao Teatro Amazonas.", "lugares-visitar", "monumentos", "Manaus", "AM", null},
            new Object[]{"Palácio Rio Negro", "Antiga residência dos governadores do Amazonas, hoje centro cultural.", "lugares-visitar", "monumentos", "Manaus", "AM", null},
            new Object[]{"Floresta Amazônica", "A maior floresta tropical do mundo, com mais de 5,5 milhões de km².", "lugares-visitar", "lugares-paradisiacos", "Amazonas", "AM", null},
            new Object[]{"Parque Nacional de Anavilhanas", "Maior arquipélago fluvial do mundo com mais de 400 ilhas no Rio Negro.", "lugares-visitar", "lugares-paradisiacos", "Novo Airão", "AM", null},
            new Object[]{"Reserva Mamirauá", "Maior reserva de várzea do mundo com 1,1 milhão de hectares.", "lugares-visitar", "lugares-paradisiacos", "Tefé", "AM", null},
            new Object[]{"Parque Nacional do Jaú", "Uma das maiores unidades de conservação da Amazônia, 2,3 milhões de hectares.", "lugares-visitar", "lugares-paradisiacos", "Novo Airão", "AM", null},
            new Object[]{"Rio Amazonas", "O maior rio do mundo em volume de água, com cerca de 6.992 km de extensão.", "lugares-visitar", "lugares-paradisiacos", "Amazonas", "AM", null},
            new Object[]{"Tacacá", "Prato típico amazônico servido em cuia com tucupi, jambu e camarão seco.", "curiosidades", "restaurantes", "Manaus", "AM", null},
            new Object[]{"Pirarucu", "Maior peixe de escamas de água doce do mundo. Considerado o bacalhau brasileiro.", "curiosidades", "restaurantes", "Amazonas", "AM", null},
            new Object[]{"Cupuaçu", "Fruto amazônico da família do cacau, usado em sucos, sorvetes e chocolates.", "curiosidades", "restaurantes", "Amazonas", "AM", null},
            new Object[]{"Açaí", "Fruto do açaizeiro, rico em antioxidantes. Base da alimentação amazônica.", "curiosidades", "restaurantes", "Amazonas", "AM", null},
            new Object[]{"Tucumã", "Fruto amazônico de polpa alaranjada, consumido em sanduíches com queijo coalho.", "curiosidades", "restaurantes", "Amazonas", "AM", null},
            new Object[]{"Farinha de Mandioca", "Ingrediente base da culinária amazônica, produzida artesanalmente há séculos.", "curiosidades", "restaurantes", "Amazonas", "AM", null},
            new Object[]{"Festival de Parintins", "Maior festival folclórico do Brasil, com os bois Garantido e Caprichoso.", "curiosidades", "costume-cultural", "Parintins", "AM", null},
            new Object[]{"Lendas Amazônicas", "Rica mitologia com Curupira, Boto-cor-de-rosa, Iara, Mapinguari e Matinta Pereira.", "curiosidades", "costume-cultural", "Amazonas", "AM", null},
            new Object[]{"Artesanato Indígena", "Arte tradicional dos povos indígenas amazônicos: cestaria, cerâmica e adornos.", "curiosidades", "costume-cultural", "Amazonas", "AM", null},
            new Object[]{"Rituais Xamânicos", "Práticas espirituais tradicionais dos povos indígenas amazônicos.", "curiosidades", "costume-cultural", "Amazonas", "AM", null},

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
            new Object[]{"Tacacá", "Caldo quente à base de tucupi e goma de tapioca com camarão seco e jambu.", "curiosidades", "restaurantes", "Belém", "PA", null},
            new Object[]{"Pato no Tucupi", "Prato emblemático do Círio de Nazaré, pato cozido em tucupi.", "curiosidades", "restaurantes", "Belém", "PA", null},
            new Object[]{"Açaí Paraense", "O verdadeiro açaí, consumido como prato principal com farinha e peixe.", "curiosidades", "restaurantes", "Belém", "PA", null},
            new Object[]{"Maniçoba", "A feijoada paraense, feita com folhas de maniva cozidas por sete dias.", "curiosidades", "restaurantes", "Belém", "PA", null},
            new Object[]{"Círio de Nazaré", "Uma das maiores procissões religiosas do mundo em Belém.", "curiosidades", "costume-cultural", "Belém", "PA", null},
            new Object[]{"Carimbó", "Dança e ritmo de origem indígena e africana com saias rodadas e tambores.", "curiosidades", "costume-cultural", "Belém", "PA", null},
            new Object[]{"Cerâmica Marajoara", "Arte ancestral com mais de mil anos, com desenhos labirínticos em vasos.", "curiosidades", "costume-cultural", "Ilha de Marajó", "PA", null},
            new Object[]{"Theatro da Paz", "Um dos mais luxuosos do Brasil, símbolo do Ciclo da Borracha em Belém.", "lugares-visitar", "monumentos", "Belém", "PA", null},
            new Object[]{"Mercado Ver-o-Peso", "Maior mercado ao ar livre da América Latina com ervas, frutos e peixes.", "lugares-visitar", "monumentos", "Belém", "PA", null},
            new Object[]{"Forte do Presépio", "Marco da fundação de Belém com museu e vista da Baía do Guajará.", "lugares-visitar", "monumentos", "Belém", "PA", null},

            // MINAS GERAIS - sigla "MG"
            new Object[]{"Ouro Preto", "Cidade histórica Patrimônio da UNESCO com arquitetura barroca do século XVIII.", "lugares-visitar", "monumentos", "Ouro Preto", "MG", "/mg/ouro-preto"},
            new Object[]{"Instituto Inhotim", "Maior museu de arte contemporânea a céu aberto do mundo em Brumadinho.", "lugares-visitar", "monumentos", "Brumadinho", "MG", "/mg/inhotim"},
            new Object[]{"Tiradentes", "Cidade histórica com arquitetura colonial preservada e gastronomia mineira.", "lugares-visitar", "monumentos", "Tiradentes", "MG", null},
            new Object[]{"Diamantina", "Cidade Patrimônio da UNESCO, berço de JK, com casarões coloniais e serras.", "lugares-visitar", "monumentos", "Diamantina", "MG", null},
            new Object[]{"Parque Estadual da Pedra Azul", "Monólito de granito com 1.822 metros que muda de cor ao longo do dia.", "lugares-visitar", "lugares-paradisiacos", "Domingos Martins", "MG", null},
            new Object[]{"Restaurante Xapuri", "Referência da culinária mineira em BH, com ambiente rústico e pratos tradicionais.", "curiosidades", "restaurantes", "Belo Horizonte", "MG", null},
            new Object[]{"Santuário do Bom Jesus de Matosinhos", "Importante santuário religioso em Congonhas com esculturas de Aleijadinho.", "lugares-visitar", "monumentos", "Congonhas", "MG", null},
            new Object[]{"Carnaval de Belo Horizonte", "Carnaval de rua com blocos tradicionais e grande diversidade cultural.", "curiosidades", "costume-cultural", "Belo Horizonte", "MG", null},

            // ESPÍRITO SANTO - sigla "ES"
            new Object[]{"Pedra Azul", "Monólito de granito com 1.822 metros no Parque Estadual da Pedra Azul.", "lugares-visitar", "lugares-paradisiacos", "Domingos Martins", "ES", "/es/pedra-azul"},
            new Object[]{"Guarapari", "Cidade litorânea famosa pelas praias de areia monazítica com propriedades terapêuticas.", "lugares-visitar", "lugares-paradisiacos", "Guarapari", "ES", "/es/guarapari"},
            new Object[]{"Convento da Penha", "Um dos santuários mais antigos do Brasil, construído no século XVI em Vila Velha.", "lugares-visitar", "monumentos", "Vila Velha", "ES", null},
            new Object[]{"Regência Augusta", "Vila de pescadores na foz do Rio Doce, famosa pela soltura de tartarugas marinhas.", "lugares-visitar", "lugares-paradisiacos", "Linhares", "ES", null},
            new Object[]{"Domingos Martins", "Cidade serrana com influência alemã, clima frio e arquitetura europeia.", "lugares-visitar", "lugares-paradisiacos", "Domingos Martins", "ES", null},
            new Object[]{"Restaurante Lareira Portuguesa", "Referência da culinária portuguesa no ES, com bacalhau e ambiente acolhedor.", "curiosidades", "restaurantes", "Vitória", "ES", null},
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
}
